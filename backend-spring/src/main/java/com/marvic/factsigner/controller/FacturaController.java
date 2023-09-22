package com.marvic.factsigner.controller;

import com.marvic.factsigner.exception.InvalidKeyException;
import com.marvic.factsigner.payload.FacturaDTO;
import com.marvic.factsigner.payload.PageResponse;
import com.marvic.factsigner.security.CustomUser;
import com.marvic.factsigner.service.FacturaService;
import com.marvic.factsigner.util.PageUtil;
import com.marvic.factsigner.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("approve/{id}")
    public ResponseEntity<String> approve(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String message = facturaService.approve(id, auth);
        return ResponseEntity.ok(message);
    }

    @GetMapping(value = "build/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> build(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String message = facturaService.buildXml(id, auth);
        return ResponseEntity.ok(message);
    }

    @GetMapping("{id}")
    public ResponseEntity<FacturaDTO> getOne(@PathVariable("id") String id) {
        FacturaDTO dto = facturaService.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public PageResponse<FacturaDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort,
            @RequestParam(value = "empresa_id", required = true) String empresaId) {
        CustomUser user = SecurityHelper.getUser();
        if (SecurityHelper.isNotPermitted(user, empresaId)) {
            throw new AccessDeniedException(String.format("%s no autorizado a consultar %s", user.getUsuarioId(), empresaId));
        }
        return facturaService.getAllByEmpresaId(empresaId, PageUtil.pagingAndSort(page, size, sort));
    }

    @PostMapping
    public ResponseEntity<FacturaDTO> save(@RequestBody @Valid FacturaDTO dto) {
        FacturaDTO saved = facturaService.create(dto);
//        if (dto.getImporteTotal() == null || dto.getImporteTotal().signum() == 0) {
//            throw new InvalidKeyException("importe total");
//        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}
