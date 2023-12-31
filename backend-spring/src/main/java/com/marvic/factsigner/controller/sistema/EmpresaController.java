package com.marvic.factsigner.controller.sistema;

import com.marvic.factsigner.payload.sistema.CertificateDTO;
import com.marvic.factsigner.payload.sistema.EmpresaDTO;
import com.marvic.factsigner.service.sistema.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService service;

    @PostMapping("{id}/setup")
    public ResponseEntity<String> setupCertificate(// @RequestParam("file") MultipartFile file,
                                                   @RequestBody @Valid CertificateDTO body,
                                                   @PathVariable("id") String id
    ) {
        service.setupCertificate(body, null, id);
        return ResponseEntity.ok("1");
    }

    @GetMapping("{id}")
    public ResponseEntity<EmpresaDTO> getOne(@PathVariable("id") String id) {
        EmpresaDTO dto = service.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<EmpresaDTO> getMany() {
        List many = service.getMany();
        return many;
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> save(@RequestBody @Valid EmpresaDTO dto) {
        EmpresaDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("1");
    }

    @PutMapping("{id}")
    public ResponseEntity<EmpresaDTO> update(@RequestBody @Valid EmpresaDTO dto, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
