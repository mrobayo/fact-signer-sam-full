package com.marvic.factsigner;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix = "factsigner")
public class EnvProperties {

    private BigDecimal limiteVenta;

    public BigDecimal getLimiteVenta() {
        return limiteVenta;
    }

    public void setLimiteVenta(BigDecimal number) {
        this.limiteVenta = number;
    }
}
