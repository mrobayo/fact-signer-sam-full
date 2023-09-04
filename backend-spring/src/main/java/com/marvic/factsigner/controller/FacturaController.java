package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.FacturaDTO;
import com.marvic.factsigner.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("approve/{id}")
    public ResponseEntity<String> approve(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String message = facturaService.approve(id, auth);
        return ResponseEntity.ok(message);
    }

    @GetMapping(value = "build/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> build(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String message = facturaService.buildXml(id, auth);
        return ResponseEntity.ok(message);
    }

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
