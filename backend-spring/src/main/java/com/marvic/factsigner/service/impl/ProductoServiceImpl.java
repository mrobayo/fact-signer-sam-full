package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.exception.ResourceExistsException;
import com.marvic.factsigner.exception.ResourceNotFoundException;

import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.model.sistema.Producto;
import com.marvic.factsigner.model.sistema.extra.Categoria;
import com.marvic.factsigner.model.sistema.extra.Grupo;
import com.marvic.factsigner.model.sistema.extra.Unidad;
import com.marvic.factsigner.model.sistema.types.ProductoTipo;

import com.marvic.factsigner.payload.PageResponse;
import com.marvic.factsigner.payload.sistema.ProductoDTO;
import com.marvic.factsigner.repository.*;
import com.marvic.factsigner.security.CustomUser;
import com.marvic.factsigner.service.sistema.ProductoService;
import com.marvic.factsigner.util.PageUtil;
import com.marvic.factsigner.util.Utils;

import com.marvic.sample.DataFiller;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.marvic.factsigner.util.Utils.coalesce;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UnidadRepository unidadRepository;
    private final ModelMapper modelMapper;

    public ProductoServiceImpl(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            UnidadRepository unidadRepository,
            ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.unidadRepository = unidadRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductoDTO getOne(String id) {
        UUID uuid = Utils.toUUID(id);
        Producto entity = productoRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return mapToDTO(entity);
    }

    @Override
    public PageResponse<ProductoDTO> getAll(String empresaId, Pageable paging) {
        Page<Producto> page = productoRepository.findAllByEmpresaId(empresaId, paging);
        return PageUtil.mapPage(page, this::mapToDTO);
    }

    @Override
    public ProductoDTO create(ProductoDTO dto) {
        // Validate
        dto.setName(StringUtils.trim(dto.getName()));

        ProductoTipo tipo = ProductoTipo.valueOf(dto.getTipo());

        // Check UK by empresa + codigo
        productoRepository
                .findByEmpresaIdAndCodigo(dto.getEmpresaId(), dto.getCodigo())
                .ifPresent((c) -> {throw new ResourceExistsException(dto.getName());});

        Producto entity = mapToEntity(dto);

        entity.setTipo(tipo);
        entity.setId(null);
        entity.setActivo(true);

        // Unidad
        Unidad unidadVenta = unidadRepository.findById(coalesce(dto.getUnidadVentaId(), "UN")).get();
        entity.setUnidadVenta(unidadVenta);

        // Categoria
        entity.setCategoria(null);
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).get();
            entity.setCategoria(categoria);
        }

        Producto saved = productoRepository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public ProductoDTO update(ProductoDTO dto, String id) {
        return null;
    }

    @Override
    public void addRandom(int num) {
        Empresa empresa = new Empresa();
        empresa.setId("1100000000001");
        Unidad unidad = new Unidad();
        unidad.setId("UN");
        Categoria categoria = categoriaRepository.findByNameAndEmpresaId("BASICO", "1100000000001").get();
        for(int i = 1; i <= num; i++) {
            Producto c = DataFiller.getProducto();
            c.setCategoria(categoria);
            c.setUnidadVenta(unidad);
            c.setEmpresa(empresa);
            productoRepository.save(c);
        }
    }

    private Producto mapToEntity(ProductoDTO dto) {
        return modelMapper.map(dto, Producto.class);
    }

    private ProductoDTO mapToDTO(Producto model){
        return modelMapper.map(model, ProductoDTO.class);
    }

}
