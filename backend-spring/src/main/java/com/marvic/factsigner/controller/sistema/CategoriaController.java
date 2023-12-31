package com.marvic.factsigner.controller.sistema;

import com.marvic.factsigner.payload.sistema.CategoriaDTO;
import com.marvic.factsigner.service.sistema.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<CategoriaDTO> getByEmpresa(
            @RequestParam(value = "empresa_id", required = true) String empresaId
    ) {
        List many = service.getAll(empresaId);
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
