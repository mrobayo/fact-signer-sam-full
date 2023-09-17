package com.marvic.factsigner.payload;


import com.marvic.factsigner.model.sistema.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
//@SuperBuilder
public class ComprobanteDTO {

    private String empresaId;

    @NotBlank @Size(max=40)
    private String puntoVentaId;

    private String name;

    private Integer secuencia;

    private String estadoDoc;

    private Boolean aprobado;

    private String aprobadoPorId;

    private String loteId;

    private String claveAcceso;

    private String tipoDoc;

    private LocalDate fechaEmision;

    private LocalDateTime fechaHora;

    private String moneda;

    // private String ambiente;

    private String autorizacion;

    private String mensajeSri;

    private Boolean emailEnviado;

    //private String sujetoTipo;
    //private String sujetoRazonSocial;
    //private String sujetoIdentidad;
    //private String sujetoDireccion;
    //private String sujetoTelefono;

    private String sujetoEmail;

}
