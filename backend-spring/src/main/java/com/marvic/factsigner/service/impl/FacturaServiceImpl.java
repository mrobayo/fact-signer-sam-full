package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.model.comprobantes.FacturaComp;
import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.model.comprobantes.types.EstadoTipo;
import com.marvic.factsigner.model.comprobantes.types.InfoFactura;
import com.marvic.factsigner.model.comprobantes.types.InfoTributaria;
import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.payload.FacturaDTO;
import com.marvic.factsigner.repository.*;
import com.marvic.factsigner.service.FacturaService;
import com.marvic.factsigner.util.Utils;
import ec.gob.sri.types.SriTipoDoc;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.transaction.Transactional;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;

    private final PuntoVentaRepository puntoVentaRepository;
    private final ClienteRepository clienteRepository;

    private final ModelMapper modelMapper;

    public FacturaServiceImpl(
            FacturaRepository facturaRepository,
            PuntoVentaRepository puntoVentaRepository,
            ClienteRepository clienteRepository,
            ModelMapper modelMapper
    ) {
        this.facturaRepository = facturaRepository;
        this.puntoVentaRepository = puntoVentaRepository;
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public FacturaDTO getOne(String id) {
        return null;
    }

    @Override
    public List<FacturaDTO> getAllByEmpresaId(String empresaId) {
        List<FacturaDTO> dtoList = facturaRepository
                .findAllByEmpresaId(empresaId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());

        return dtoList;
    }

    @Override
    public FacturaDTO create(FacturaDTO dto) {

        PuntoVenta puntoVenta = puntoVentaRepository.findById(dto.getPuntoVentaId()).get();
        Empresa empresa = puntoVenta.getEmpresa();
        UUID compradorUuid = Utils.toUUID(dto.getCompradorId());
        Cliente comprador = clienteRepository.findById(compradorUuid).get();

        // Check UK by name + empresa
        if (dto.getName() != null) {
            facturaRepository.findByNameAndEmpresaId(dto.getName(), empresa.getId())
                    .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});
        }

        FacturaComp entity = mapToEntity(dto);

        // InfoTributaria
        modelMapper.map(dto, entity.getInfoTributaria());

        // Basic
        entity.setPuntoVenta(puntoVenta);
        entity.setEstadoDoc(EstadoTipo.BORRADOR);
        entity.setEmailEnviado(false);
        entity.setAprobador(null);

        // InfoFactura
        entity.setEmpresa(empresa);
        entity.getInfoTributaria().setAmbienteSri(empresa.getAmbiente());
        entity.getInfoTributaria().setTipoDoc(SriTipoDoc.FACTURA);

        entity.setMoneda(empresa.getMoneda());
        entity.setObligadoContabilidad(empresa.isObligado());
        entity.setContribuyenteEspecial(empresa.getNumeroContribuyente());

        // Factura
        entity.setTotalSinImpuestos(ZERO);
        entity.setTotalDescuento(ZERO);
        entity.setPropina(ZERO);
        entity.setImporteTotal(ZERO);

        // Comprador
        entity.setComprador(comprador);
        if (comprador != null) {
            entity.setTipoIdentificacionComprador(comprador.getTipo());
            entity.setRazonSocialComprador(comprador.getName());
            entity.setIdentificacionComprador(comprador.getIdentidad());
            entity.setDireccionComprador(comprador.getDireccion());
            entity.setSujetoEmail(comprador.getEmail());
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
        comp.setInfoTributaria(new InfoTributaria());
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

}
