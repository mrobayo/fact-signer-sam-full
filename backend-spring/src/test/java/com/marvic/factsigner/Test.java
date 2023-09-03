package com.marvic.factsigner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvic.factsigner.model.comprobantes.FacturaComp;
import com.marvic.factsigner.model.comprobantes.types.InfoTributaria;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import ec.gob.sri.types.SriAmbiente;
import ec.gob.sri.types.SriTipoDoc;
import org.modelmapper.ModelMapper;

public class Test {

    public static void main(String[] args) {
        FacturaComp comp = new FacturaComp();
        comp.setInfoTributaria(new InfoTributaria());
        comp.getInfoTributaria().setAmbienteSri(SriAmbiente.PRUEBAS);
        comp.getInfoTributaria().setTipoDoc(SriTipoDoc.FACTURA);

        comp.getInfoTributaria().setName("123-456-000000001");
        comp.getInfoTributaria().setSecuencia(54321);

        ModelMapper mapper = new ModelMapper();
        //Factura xml = new Factura();

        Factura xml = mapper.map(comp, Factura.class);
        System.out.println(xml);

    }

}
