package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.payload.auth.JWTAuthResponse;
import com.marvic.factsigner.payload.auth.LoginDto;
import com.marvic.factsigner.payload.auth.RegisterDto;
import com.marvic.factsigner.repository.UsuarioRepository;
import com.marvic.factsigner.security.CustomUser;
import com.marvic.factsigner.security.JwtTokenProvider;
import com.marvic.factsigner.service.AuthService;

import org.apache.commons.lang3.NotImplementedException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService  {

    private final UsuarioRepository usuarioRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UsuarioRepository usuarioRepository,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JWTAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateToken(authentication);

        CustomUser principal = (CustomUser) authentication.getPrincipal();
        return new JWTAuthResponse(jwtToken, principal.getPuntos());
    }

    @Override
    public String register(RegisterDto dto) {
        throw new NotImplementedException("Disabled - Auth is required");
    }
}
