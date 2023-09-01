package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.model.comprobantes.FacturaComp;
import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.model.comprobantes.types.EstadoTipo;
import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.payload.FacturaDTO;
import com.marvic.factsigner.repository.*;
import com.marvic.factsigner.service.FacturaService;
import com.marvic.factsigner.util.Utils;
import ec.gob.sri.types.SriTipoDoc;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.math.BigDecimal.ZERO;

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
        entity.setTotalDescuento(ZERO);
        entity.setPropina(ZERO);
        entity.setTotalSinImpuestos(ZERO);
        entity.setImporteTotal(ZERO);

        entity.setComprador(comprador);
        entity.setEmpresa(puntoVenta.getEmpresa());
        entity.setPuntoVenta(puntoVenta);

        entity.setEstadoDoc(EstadoTipo.BORRADOR);
        entity.setTipoDoc(SriTipoDoc.FACTURA);
        entity.setMoneda(empresa.getMoneda());

        entity.setAmbiente(empresa.getAmbiente());
        entity.setObligado(empresa.isObligado());
        entity.setNumeroContribuyente(empresa.getNumeroContribuyente());
        entity.setEmailEnviado(false);

        entity.setSujetoTipo(comprador.getTipo());
        entity.setSujetoRazonSocial(comprador.getName());
        entity.setSujetoIdentidad(comprador.getIdentidad());
        entity.setSujetoDireccion(comprador.getDireccion());
        entity.setSujetoTelefono(comprador.getTelefono());
        entity.setSujetoEmail(comprador.getEmail());

        entity.setAprobador(null);

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
        return modelMapper.map(dto, FacturaComp.class);
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
