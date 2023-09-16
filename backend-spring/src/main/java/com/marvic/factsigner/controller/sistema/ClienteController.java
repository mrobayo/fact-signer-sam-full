package com.marvic.factsigner.controller.sistema;

import com.marvic.factsigner.payload.PageResponse;
import com.marvic.factsigner.payload.sistema.ClienteDTO;
import com.marvic.factsigner.service.sistema.ClienteService;
import com.marvic.factsigner.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/random")
    public ResponseEntity<String> random(@RequestParam("num") int num) {
        service.addRandom(num);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("{id}")
    public ResponseEntity<ClienteDTO> getOne(@PathVariable("id") String id) {
        ClienteDTO dto = service.getOne(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * ?page=1&size=5&sort=published,desc&sort=title,asc
     */
    @GetMapping
    public PageResponse<ClienteDTO> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "all") String activo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        return service.getAll(search, activo, PageUtil.pagingAndSort(page, size, sort));
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
