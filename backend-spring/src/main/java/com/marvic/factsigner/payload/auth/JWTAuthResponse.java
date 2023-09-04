package com.marvic.factsigner.payload.auth;

import com.marvic.factsigner.payload.sistema.PuntoVentaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {

    private String usuarioId;
    private String accessToken;
    private final String tokenType = "Bearer";

    // Listado de puntos de venta que el usuario puede usar
    private PuntoVentaDTO puntoVenta;
}
