package com.marvic.factsigner.controller.sistema;

import com.marvic.factsigner.payload.sistema.PuntoSecuenciaDTO;
import com.marvic.factsigner.service.PuntoSecuenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
