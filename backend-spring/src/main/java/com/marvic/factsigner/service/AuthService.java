package com.marvic.factsigner.service;

import com.marvic.factsigner.payload.auth.JWTAuthResponse;
import com.marvic.factsigner.payload.auth.LoginDto;
import com.marvic.factsigner.payload.auth.RegisterDto;

public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
