package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.PuntoVentaDTO;
import com.marvic.factsigner.service.PuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/punto-ventas")
public class PuntoVentaController {

    @Autowired
    private PuntoVentaService service;

    @GetMapping("{id}")
    public ResponseEntity<PuntoVentaDTO> getOne(@PathVariable("id") String id) {
        PuntoVentaDTO dto = service.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<PuntoVentaDTO> getByEmpresa(
            @RequestParam(value = "empresa_id", required = true) String empresaId
    ) {
        List many = service.getAll(empresaId);
        return many;
    }

    @PostMapping
    public ResponseEntity<PuntoVentaDTO> save(@RequestBody @Valid PuntoVentaDTO dto) {
        PuntoVentaDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("1");
    }

    @PutMapping("{id}")
    public ResponseEntity<PuntoVentaDTO> update(@RequestBody @Valid PuntoVentaDTO dto, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
