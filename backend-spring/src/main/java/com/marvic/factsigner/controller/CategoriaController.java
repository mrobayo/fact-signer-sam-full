package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.CategoriaDTO;
import com.marvic.factsigner.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping("{id}")
    public ResponseEntity<CategoriaDTO> getOne(@PathVariable("id") Integer id) {
        CategoriaDTO dto = service.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<CategoriaDTO> getMany() {
        List many = service.getMany("");
        return many;
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> save(@RequestBody @Valid CategoriaDTO dto) {
        CategoriaDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok("1");
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoriaDTO> update(@RequestBody @Valid CategoriaDTO dto, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
