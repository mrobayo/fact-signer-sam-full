package com.marvic.factsigner.model.comprobantes;

import ec.gob.sri.types.SriTipoDoc;

public final class Util {

    /**
     * Document Path: {empresa}/{year}/{tipo}/{tipo}-{numero}-{hash}.signed.xml
     * Ejemplo: 0912345678001/2023/factura/factura-001-001-0000001-twk.signed.xml
     */
    public static String documentPath(Comprobante entity, boolean useClaveAcceso) {
        SriTipoDoc tipoDoc = entity.getTipoDoc();
        Integer year = entity.getFechaEmision().getYear();
        String fileName = useClaveAcceso ? entity.getClaveAcceso() : entity.getName();
        String ruc = entity.getRuc();
        return String.format("%s/%s/%s/%s-%s", ruc, year, tipoDoc.name(), tipoDoc.name(), fileName);
    }

}
