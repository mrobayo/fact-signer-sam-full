package com.marvic.factsigner.model;

import com.marvic.factsigner.exception.ComprobanteException;
import com.marvic.factsigner.model.comprobantes.Comprobante;
import com.marvic.factsigner.model.sistema.Empresa;
import com.marvic.factsigner.util.AesCipher;
import com.marvic.factsigner.util.Utils;
import ec.gob.sri.types.SriTipoDoc;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

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

    public static ImmutablePair<String, InputStream> getCertificate(Empresa entity) {
        if (StringUtils.isEmpty(entity.getCertFile()) || StringUtils.isEmpty(entity.getClaveCert())) {
            throw new ComprobanteException(HttpStatus.BAD_REQUEST, "Configurar Certificado y/o Clave");
        }
        try {
            String certKey = getCipher(entity).decrypt(entity.getClaveCert());
            InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(entity.getCertFile()));

            return ImmutablePair.of(certKey, in);
        } catch (Exception exception) {
            String message = Utils.coalesce(exception.getMessage(), exception.getCause() != null ? exception.getCause().getMessage() : exception.toString());
            throw new ComprobanteException(HttpStatus.INTERNAL_SERVER_ERROR, message);
        }
    }

    public static AesCipher getCipher(Empresa empresa) {
        return new AesCipher(empresa.getId());
    }

}
