package com.marvic.factsigner.controller.sistema;

import com.marvic.factsigner.payload.sistema.ClienteDTO;
import com.marvic.factsigner.service.sistema.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("{id}")
    public ResponseEntity<ClienteDTO> getOne(@PathVariable("id") String id) {
        ClienteDTO dto = service.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<ClienteDTO> getAll() {
        List<ClienteDTO> all = service.getAll();
        return all;
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> save(@RequestBody @Valid ClienteDTO dto) {
        ClienteDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("1");
    }

    @PutMapping("{id}")
    public ResponseEntity<ClienteDTO> update(@RequestBody @Valid ClienteDTO dto, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
