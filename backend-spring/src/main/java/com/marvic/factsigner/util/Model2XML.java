package com.marvic.factsigner.util;

import com.marvic.factsigner.model.comprobantes.FacturaComp;
import ec.gob.sri.comprobantes.modelo.factura.Factura;

public class Model2XML {

    public static final String ID = "comprobante";
    public static final String VERSION = "1.1.0";


    Factura generarComprobante(FacturaComp entity) {
        Factura xml = new Factura();
        xml.setId(ID);
        xml.setVersion(VERSION);

//        xml.setInfoTributaria(factory.createInfoTributaria());
//        xml.setInfoFactura(factory.createFacturaInfoFactura());
//        xml.setDetalles(factory.createFacturaDetalles());
//
//        infoTributaria(f.getInfoTributaria());
//        infoDetalles(f.getDetalles(), factory);
//        infoFactura(f.getInfoFactura(), factory);
//        infoExportacion(f.getInfoFactura(), ec);
//
//        InfoAdicional adicional = infoAdicional(factory, infoAdicional);
//        if (adicional != null) xml.setInfoAdicional(adicional);

        return xml;
    }

}
