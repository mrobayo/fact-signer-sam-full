package com.marvic.factsigner.service.sistema;

import com.marvic.factsigner.payload.sistema.ProductoDTO;
import com.marvic.factsigner.security.CustomUser;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {

    ProductoDTO getOne(String id);

    List<ProductoDTO> getAll(String empresaId, Pageable paging);

    ProductoDTO create(ProductoDTO dto);

    void delete(String id);

    ProductoDTO update(ProductoDTO dto, String id);

    void addRandom(int num);
}
