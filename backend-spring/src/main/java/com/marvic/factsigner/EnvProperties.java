package com.marvic.factsigner;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
@ConfigurationProperties(prefix = "factsigner")
public class EnvProperties {

    private BigDecimal limiteVenta;

    // jwt-secret
    // jwt-expiration-milliseconds
    private String jwtSecret;
    private String jwtExpirationMilliseconds;

}
