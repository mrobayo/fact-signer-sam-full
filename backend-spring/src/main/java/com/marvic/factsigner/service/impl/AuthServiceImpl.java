package com.marvic.factsigner.service.impl;

import com.marvic.factsigner.payload.auth.JWTAuthResponse;
import com.marvic.factsigner.payload.auth.LoginDto;
import com.marvic.factsigner.payload.auth.RegisterDto;
import com.marvic.factsigner.security.CustomUser;
import com.marvic.factsigner.security.JwtTokenProvider;
import com.marvic.factsigner.service.sistema.AuthService;

import org.apache.commons.lang3.NotImplementedException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService  {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JWTAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateToken(authentication);

        CustomUser principal = (CustomUser) authentication.getPrincipal();

        String[] roles = null;
        if (principal.getAuthorities() != null) {
            roles = principal.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .toArray(String[]::new);
        }

        return new JWTAuthResponse(principal.getUsuarioId(), jwtToken, null, principal.getEmail(), roles);
    }

    @Override
    public String register(RegisterDto dto) {
        throw new NotImplementedException("Disabled - Auth is required");
    }
}
