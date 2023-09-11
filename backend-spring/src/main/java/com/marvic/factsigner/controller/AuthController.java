package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.auth.JWTAuthResponse;
import com.marvic.factsigner.payload.auth.LoginDto;
import com.marvic.factsigner.payload.auth.RegisterDto;
import com.marvic.factsigner.payload.sistema.PuntoVentaDTO;
import com.marvic.factsigner.service.sistema.AuthService;
import com.marvic.factsigner.service.PuntoVentaService;
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
        JWTAuthResponse response = authService.login(loginDto);
        PuntoVentaDTO puntoVenta = puntoVentaService.findPuntoVentaAutorizado(response.getUsuarioId(), loginDto.getEmpresaId());
        response.setPuntoVenta(puntoVenta);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
