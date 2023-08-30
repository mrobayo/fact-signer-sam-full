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

    // 1 dias =  86400000 ms
    // 7 dias = 604800000 ms
    private String jwtExpirationMilliseconds;

    public static final long ONE_DAY_MS = 86400000L;

}
