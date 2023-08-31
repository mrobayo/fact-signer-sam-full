package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.ProductoDTO;

import java.util.List;

public interface ProductoService {

    ProductoDTO getOne(String id);

    List<ProductoDTO> getAll(String empresaId);

    ProductoDTO create(ProductoDTO dto);

    void delete(String id);

    ProductoDTO update(ProductoDTO dto, String id);
}
