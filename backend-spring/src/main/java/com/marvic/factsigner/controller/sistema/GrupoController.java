package com.marvic.factsigner.controller.sistema;

import com.marvic.factsigner.payload.sistema.GrupoDTO;
import com.marvic.factsigner.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService service;

    @GetMapping("{id}")
    public ResponseEntity<GrupoDTO> getOne(@PathVariable("id") String id) {
        GrupoDTO dto = service.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<GrupoDTO> getAll() {
        List<GrupoDTO> all = service.getAll();
        return all;
    }

    @PostMapping
    public ResponseEntity<GrupoDTO> save(@RequestBody @Valid GrupoDTO dto) {
        GrupoDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("1");
    }

    @PutMapping("{id}")
    public ResponseEntity<GrupoDTO> update(@RequestBody @Valid GrupoDTO dto, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
