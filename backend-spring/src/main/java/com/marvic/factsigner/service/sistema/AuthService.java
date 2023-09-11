package com.marvic.factsigner.service.sistema;

import com.marvic.factsigner.payload.auth.JWTAuthResponse;
import com.marvic.factsigner.payload.auth.LoginRequestDto;
import com.marvic.factsigner.payload.auth.RegisterDto;

public interface AuthService {
    JWTAuthResponse login(LoginRequestDto loginDto);

    String register(RegisterDto registerDto);
}
