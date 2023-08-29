package com.marvic.factsigner.repository;

import com.marvic.factsigner.model.sistema.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

}
