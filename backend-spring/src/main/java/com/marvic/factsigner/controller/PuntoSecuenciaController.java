package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.CategoriaDTO;
import com.marvic.factsigner.payload.PuntoSecuenciaDTO;
import com.marvic.factsigner.service.CategoriaService;
import com.marvic.factsigner.service.PuntoSecuenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/secuencias")
public class PuntoSecuenciaController {

    @Autowired
    private PuntoSecuenciaService service;

    @PutMapping("{id}")
    public ResponseEntity<PuntoSecuenciaDTO> update(@RequestBody @Valid PuntoSecuenciaDTO dto, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
