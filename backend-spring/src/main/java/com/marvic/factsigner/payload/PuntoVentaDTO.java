package com.marvic.factsigner.payload;
import com.marvic.factsigner.model.sistema.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PuntoVentaDTO {

    private UUID id;

    private Empresa empresa;

    @NotEmpty
    private String nombre;

    private boolean activo;

    private boolean matriz = false;

    @NotEmpty
    private String estab;

    @NotEmpty
    private String ptoEmi;

    @NotEmpty
    private String direccion;

    @NotEmpty
    private String telefono;

    private List<PuntoSecuenciaDTO> secuencias = new ArrayList<>();

}
