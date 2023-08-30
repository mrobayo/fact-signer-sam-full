package com.marvic.factsigner.controller;

import com.marvic.factsigner.payload.UsuarioDTO;
import com.marvic.factsigner.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@RestController
//@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService service;


}
