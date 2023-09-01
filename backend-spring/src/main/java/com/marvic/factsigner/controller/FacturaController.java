package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.FacturaDTO;
import com.marvic.factsigner.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("{id}")
    public ResponseEntity<FacturaDTO> getOne(@PathVariable("id") String id) {
        FacturaDTO dto = facturaService.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<FacturaDTO> getAll(@RequestParam(value = "empresa_id") String empresaId) {
        List<FacturaDTO> all = facturaService.getAllByEmpresaId(empresaId);
        return all;
    }

    @PostMapping
    public ResponseEntity<FacturaDTO> save(@RequestBody @Valid FacturaDTO dto) {
        FacturaDTO saved = facturaService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}
