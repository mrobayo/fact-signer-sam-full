package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.payload.PageResponse;
import com.marvic.factsigner.service.aws.S3Service;
import com.marvic.factsigner.exception.ComprobanteException;
import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.exception.ResourceNotFoundException;
import com.marvic.factsigner.model.comprobantes.FacturaComp;
import com.marvic.factsigner.model.Util;
import com.marvic.factsigner.model.comprobantes.extra.PuntoSecuencia;
import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.model.comprobantes.types.EstadoTipo;
import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.payload.FacturaDTO;
import com.marvic.factsigner.repository.*;
import com.marvic.factsigner.service.FacturaService;
import com.marvic.factsigner.util.Model2XML;
import com.marvic.factsigner.util.PageUtil;
import com.marvic.factsigner.util.SriUtil;
import com.marvic.factsigner.util.Utils;

import ec.gob.sri.comprobantes.modelo.factura.Factura;
import com.marvic.factsigner.service.SignerService;
import ec.gob.sri.types.SriTipoDoc;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import static java.math.BigDecimal.ZERO;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
@Transactional
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;

    private final PuntoVentaRepository puntoVentaRepository;

    private final PuntoSecuenciaRepository secuenciaRepository;

    private final ClienteRepository clienteRepository;

    private final ModelMapper modelMapper;

    private final ModelMapper skipModelMapper;

    private final S3Service s3Service;

    private final SignerService signerService;

    public FacturaServiceImpl(
            FacturaRepository facturaRepository,
            PuntoVentaRepository puntoVentaRepository,
            PuntoSecuenciaRepository secuenciaRepository,
            ClienteRepository clienteRepository,
            ModelMapper modelMapper,
            S3Service s3Service,
            SignerService signerService
    ) {
        this.facturaRepository = facturaRepository;
        this.puntoVentaRepository = puntoVentaRepository;
        this.secuenciaRepository = secuenciaRepository;
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
        this.s3Service = s3Service;
        this.signerService = signerService;

        this.skipModelMapper = new ModelMapper();
        skipModelMapper.addMappings(skipModifiedFieldsMap);
    }

    PropertyMap<FacturaComp, FacturaDTO> skipModifiedFieldsMap = new PropertyMap<FacturaComp, FacturaDTO>() {
        protected void configure() {
            skip().setDetalles(null);
        }
    };

    private FacturaComp getById(String id) {
        return facturaRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    private Integer siguienteSecuencia(String puntoVentaId, SriTipoDoc tipo) {
        PuntoSecuencia punto = secuenciaRepository
                .lockById(Utils.secuenciaId(puntoVentaId, tipo))
                .orElseThrow(() -> new ResourceNotFoundException("secuencia not found"));

        Integer secuencia = punto.getSecuencia();
        punto.setSecuencia( secuencia + 1);

        secuenciaRepository.save(punto);
        return secuencia;
    }

    @Override
    public String approve(String id, Authentication auth) {
        FacturaComp entity = getById(id);
        if (entity.isAprobado()) {
            throw new ComprobanteException(HttpStatus.BAD_REQUEST, "Comprobante ya ha sido aprobado y emitido.");
        }

        entity.setAprobado(true);
        entity.setAprobador(auth.getName());
        entity.setFechaAprobado(LocalDateTime.now().withSecond(0));
        entity.setSecuencia(siguienteSecuencia(
                entity.getPuntoVenta().getId(), entity.getTipoDoc())
        );
        entity.setFechaEmision(LocalDate.now());
        entity.setFechaHora(LocalDateTime.now().withSecond(0));
        entity.setName(String.format("%s-%s-%09d",
                entity.getPuntoVenta().getEstab(),
                entity.getPuntoVenta().getPtoEmi(),
                entity.getSecuencia())
        );
        String claveAcceso = SriUtil.claveAcceso(
                Utils.fmtDMY(entity.getFechaEmision()),
                entity.getTipoDoc(),
                entity.getRuc(), // RUC
                entity.getAmbienteSri(),
                entity.getPuntoVenta().getEstab(),
                entity.getPuntoVenta().getPtoEmi(),
                entity.getSecuencial(),
                "00000000"
        );
        entity.setClaveAcceso( claveAcceso );
        facturaRepository.save(entity);
        return "1";
    }



    /**
     * Genera el archivo XML cuando la factura ha sido aprobada
     */
    @Override
    public String buildXml(String id, Authentication auth) {
        FacturaComp entity = getById(id);
        Empresa empresa = entity.getEmpresa();

        String xmlContent;
        try {
            Factura xmlObject = Model2XML.generarComprobante(entity);
            xmlContent = SriUtil.xmlNotSigned(xmlObject, SriTipoDoc.FACTURA);
        } catch (Exception exception) {
            String message = Utils.coalesce(exception.getMessage(), exception.getCause() != null ? exception.getCause().getMessage() : exception.toString());
            throw new ComprobanteException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al ensamblar XML - " + message);
        }

        ImmutablePair<String, InputStream> certificate = Util.getCertificate(empresa);

        String url;
        try (InputStream inCert = certificate.getRight()) {
            Document document = signerService.signDocument(xmlContent, inCert, certificate.getLeft());

            //Save file in amazon AWS
            String filepath = Util.documentPath(entity, false) + ".signed.xml";
            url = s3Service.saveObject("fact-signer-bucket", filepath, document);

        } catch (Exception exception) {
            String message = Utils.coalesce(exception.getMessage(), exception.getCause() != null ? exception.getCause().getMessage() : exception.toString());
            throw new ComprobanteException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al firmar XML - " + message);
        }

        entity.setDocumentUrl(url);
        entity.setEstadoDoc(EstadoTipo.EMITIDO);

        return url;
    }

    @Override @Transactional(readOnly = true)
    public FacturaDTO getOne(String id) {
        FacturaComp entity = getById(id);
        return mapToDTO(entity);
    }

    @Override @Transactional(readOnly = true)
    public PageResponse<FacturaDTO> getAllByEmpresaId(String empresaId, Pageable paging) {
        Page<FacturaComp> page = facturaRepository.findAllByEmpresaId(empresaId, paging);
        return PageUtil.mapPage(page, this::skipToDTO);
    }

    private static void copyProperties(FacturaComp entity, FacturaDTO dto) {
        BeanUtils.copyProperties(entity, dto, "detalles");
    }

    @Override
    public FacturaDTO create(FacturaDTO dto) {

        PuntoVenta puntoVenta = puntoVentaRepository.findById(dto.getPuntoVentaId()).get();
        Empresa empresa = puntoVenta.getEmpresa();
        UUID clienteId = Utils.toUUID(dto.getClienteId());
        Cliente cliente = clienteRepository.findById(clienteId).get();

        // Check UK by name + empresa
        if (dto.getName() != null) {
            facturaRepository.findByNameAndEmpresaId(dto.getName(), empresa.getId())
                    .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});
        }

        FacturaComp entity = mapToEntity(dto);

        // InfoTributaria
        // modelMapper.map(dto, entity.getInfoTributaria());

        // Basic
        entity.setPuntoVenta(puntoVenta);
        entity.setEstadoDoc(EstadoTipo.BORRADOR);
        entity.setEmailEnviado(false);
        entity.setAprobador(null);

        // InfoFactura
        entity.setEmpresa(empresa);
        entity.setAmbienteSri(empresa.getAmbiente());
        entity.setTipoDoc(SriTipoDoc.FACTURA);

        entity.setMoneda(empresa.getMoneda());
        entity.setObligadoContabilidad(empresa.isObligado());
        entity.setContribuyenteEspecial(empresa.getNumeroContribuyente());

        // Factura
        entity.setTotalSinImpuestos(ZERO);
        entity.setTotalDescuento(ZERO);
        entity.setPropina(ZERO);
        entity.setImporteTotal(ZERO);

        // Comprador
        entity.setCliente(cliente);
        if (cliente != null) {
            entity.setTipoIdentificacionComprador(cliente.getTipo());
            entity.setRazonSocialComprador(cliente.getName());
            entity.setIdentificacionComprador(cliente.getIdentidad());
            entity.setDireccionComprador(cliente.getDireccion());
            entity.setSujetoEmail(cliente.getEmail());
        }

        if (entity.getDetalles() != null) {
            entity.getDetalles().forEach((detalle) -> {
                detalle.setItem(null);
            });
        }

        FacturaComp saved = facturaRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public FacturaDTO update(FacturaDTO dto, String id) {
        return null;
    }

    private FacturaComp mapToEntity(FacturaDTO dto) {
        FacturaComp comp = modelMapper.map(dto, FacturaComp.class);
        // comp.setInfoTributaria(new InfoTributaria());
        // comp.setInfoFactura(new InfoFactura());
        return comp;
    }

    private FacturaDTO mapToDTO(FacturaComp model){
        FacturaDTO dto = modelMapper.map(model, FacturaDTO.class);
        if (model.getId() != null) {
            dto.setId(model.getId().toString());
        }
        else {
            dto.setId(null);
        }
        return dto;
    }

    private FacturaDTO skipToDTO(FacturaComp model){
        FacturaDTO dto = skipModelMapper.map(model, FacturaDTO.class);
        if (model.getId() != null) {
            dto.setId(model.getId().toString());
        }
        else {
            dto.setId(null);
        }
        return dto;
    }

}
