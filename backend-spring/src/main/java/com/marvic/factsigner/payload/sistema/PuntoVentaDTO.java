package com.marvic.factsigner.payload.sistema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuntoVentaDTO {

    private String id;

    @NotBlank @Size(max=20)
    private String empresaId;

    private String name;

    private boolean activo;

    private boolean matriz = false;

    @NotBlank @Size(min=3, max=3)
    private String estab;

    @NotBlank @Size(min=3, max=3)
    private String ptoEmi;

    @NotBlank @Size(min=1, max=100)
    private String direccion;

    @NotBlank @Size(min=7, max=60)
    private String telefono;

    private List<PuntoSecuenciaDTO> secuencias = new ArrayList<>();

    private EmpresaDTO empresa;

}
