package com.marvic.factsigner.controller;

import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.payload.auth.JWTAuthResponse;
import com.marvic.factsigner.payload.auth.LoginDto;
import com.marvic.factsigner.payload.auth.RegisterDto;
import com.marvic.factsigner.payload.sistema.PuntoVentaDTO;
import com.marvic.factsigner.service.AuthService;
import com.marvic.factsigner.service.PuntoVentaService;
import com.marvic.factsigner.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * https://www.javainuse.com/spring/boot-jwt
 * https://www.appsdeveloperblog.com/add-and-validate-custom-claims-in-jwt/
 */
// @CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PuntoVentaService puntoVentaService;

    public AuthController(AuthService authService, PuntoVentaService puntoVentaService) {
        this.authService = authService;
        this.puntoVentaService = puntoVentaService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody @Valid LoginDto loginDto) {
        JWTAuthResponse token = authService.login(loginDto);
        PuntoVentaDTO puntoVenta = puntoVentaService.findPuntoVentaAutorizado(token.getUsuarioId(), loginDto.getEmpresaId());
        token.setPuntoVenta(puntoVenta);
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
