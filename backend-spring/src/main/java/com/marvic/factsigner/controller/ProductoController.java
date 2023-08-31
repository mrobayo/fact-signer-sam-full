package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.ProductoDTO;
import com.marvic.factsigner.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping("{id}")
    public ResponseEntity<ProductoDTO> getOne(@PathVariable("id") String id) {
        ProductoDTO dto = service.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<ProductoDTO> getAll(@RequestParam(value = "empresa_id", required = true) String empresaId) {
        List<ProductoDTO> all = service.getAll(empresaId);
        return all;
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> save(@RequestBody @Valid ProductoDTO dto) {
        ProductoDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("1");
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductoDTO> update(@RequestBody @Valid ProductoDTO dto, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
