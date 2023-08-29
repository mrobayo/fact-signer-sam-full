package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, String> {

}
