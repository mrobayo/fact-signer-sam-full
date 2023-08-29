package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.UsuarioDTO;
import com.marvic.factsigner.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

//@RestController
//@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable("id") Integer usuarioId) {
        UsuarioDTO usuarioDto = usuarioService.getUsuario(usuarioId);
        return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping
    public List<UsuarioDTO> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> saveUsuario(@RequestBody @Valid UsuarioDTO dto) {
        UsuarioDTO saved = usuarioService.createUsuario(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable("id") Integer usuarioId) {
        usuarioService.deleteUsuario(usuarioId);
        return ResponseEntity.ok("Usuario eliminado exitosamente!");
    }

    @PutMapping("{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@RequestBody UsuarioDTO dto, @PathVariable("id") Integer usuarioId) {
        return ResponseEntity.ok(usuarioService.updateUsuario(dto, usuarioId));
    }

}
