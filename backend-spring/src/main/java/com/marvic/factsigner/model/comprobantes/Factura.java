package com.marvic.factsigner.model.comprobantes;

import com.marvic.factsigner.model.comprobantes.extra.PuntoVenta;
import com.marvic.factsigner.model.sistema.Usuario;
import ec.gob.sri.types.SriAmbiente;
import ec.gob.sri.types.SriEnumIdentidad;
import ec.gob.sri.types.SriTipoDoc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

//@Entity
//@Table(name="si_factura",
//        uniqueConstraints = @UniqueConstraint(name="si_factura_numero_uk", columnNames = {"numero", "empresaId"}))
public class Factura {

    @Id
    private String id;


    //private AuditObject audit;
    //private List<DatoAdicional> adicionales = new ArrayList<DatoAdicional>();

}
