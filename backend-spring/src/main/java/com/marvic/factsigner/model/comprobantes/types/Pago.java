package com.marvic.factsigner.model.comprobantes.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor

@Embeddable
public class Pago {

    private String formaPago;

    private Integer plazo;

    private BigDecimal total;

    public static Map<String, String> FORMAS =
            Map.of(
                    "01", "SIN UTILIZACION DEL SISTEMA FINANCIERO",
                    "15", "COMPENSACION DE DEUDAS",
                    "16", "TARJETA DE DEBITO",
                    "17", "DINERO ELECTRONICO",
                    "18", "TARJETA PREPAGO",
                    "19", "TARJETA DE CREDITO",
                    "20", "OTROS CON UTILIZACION DEL SISTEMA FINANCIERO",
                    "21", "ENDOSO DE TITULOS");

}
