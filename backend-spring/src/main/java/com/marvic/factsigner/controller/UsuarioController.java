package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.UsuarioDTO;
import com.marvic.factsigner.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> getOne(@PathVariable("id") String id) {
        UsuarioDTO usuarioDto = service.getOne(id);
        return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping
    public List<UsuarioDTO> getUsuarios() {
        return service.getMany();
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody @Valid UsuarioDTO dto) {
        UsuarioDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("eliminado exitosamente!");
    }

    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioDTO dto, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
