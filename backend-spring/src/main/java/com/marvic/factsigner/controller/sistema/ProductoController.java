package com.marvic.factsigner.controller.sistema;

import com.marvic.factsigner.payload.PageResponse;
import com.marvic.factsigner.payload.sistema.ProductoDTO;
import com.marvic.factsigner.security.CustomUser;
import com.marvic.factsigner.service.sistema.ProductoService;
import com.marvic.factsigner.util.PageUtil;
import com.marvic.factsigner.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping("/random")
    public ResponseEntity<String> random(@RequestParam("num") int num) {
        service.addRandom(num);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductoDTO> getOne(@PathVariable("id") String id) {
        ProductoDTO dto = service.getOne(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public PageResponse<ProductoDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort,
            @RequestParam(value = "empresa_id", required = true) String empresaId) {
        CustomUser user = SecurityHelper.getUser();
        if (SecurityHelper.isNotPermitted(user, empresaId)) {
            throw new AccessDeniedException(String.format("%s no autorizado a consultar %s", user.getUsuarioId(), empresaId));
        }
        return service.getAll(empresaId, PageUtil.pagingAndSort(page, size, sort));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> save(@RequestBody @Valid ProductoDTO dto) {
        ProductoDTO saved = service.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.ok("1");
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductoDTO> update(@RequestBody @Valid ProductoDTO dto, @PathVariable("id") String id) {
        return ResponseEntity.ok(service.update(dto, id));
    }

}
