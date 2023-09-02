package com.marvic.factsigner.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private final String tokenType = "Bearer";

    // Listado de puntos de venta que el usuario puede usar
    private String[] puntos;
}
