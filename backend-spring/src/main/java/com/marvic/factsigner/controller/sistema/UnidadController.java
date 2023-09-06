package com.marvic.factsigner.controller.sistema;

import com.marvic.factsigner.payload.sistema.UnidadDTO;
import com.marvic.factsigner.service.sistema.UnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/unidades")
public class UnidadController {

    @Autowired
    private UnidadService service;

    @GetMapping("{id}")
    public ResponseEntity<UnidadDTO> getOne(@PathVariable("id") String id) {
        UnidadDTO dto = service.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<UnidadDTO> getMany() {
        List many = service.getMany();
        return many;
    }

    @PostMapping
    public ResponseEntity<UnidadDTO> save(@RequestBody @Valid UnidadDTO dto) {
        UnidadDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("1");
    }

    @PutMapping("{id}")
    public ResponseEntity<UnidadDTO> update(@RequestBody @Valid UnidadDTO dto, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
