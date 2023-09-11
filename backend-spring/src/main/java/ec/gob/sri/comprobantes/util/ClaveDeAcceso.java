package ec.gob.sri.comprobantes.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

public class ClaveDeAcceso {
    private String claveGenerada;

    public ClaveDeAcceso() {
    }

    public String generaClave(Date fechaEmision, String tipoComprobante, String ruc, String ambiente, String serie, String numeroComprobante, String codigoNumerico, String tipoEmision) {
        //int verificador = false;
        if (ruc != null && ruc.length() < 13) {
            ruc = String.format("%013d", ruc);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fecha = dateFormat.format(fechaEmision);
        StringBuilder clave = new StringBuilder(fecha);
        clave.append(tipoComprobante);
        clave.append(ruc);
        clave.append(ambiente);
        clave.append(serie);
        clave.append(numeroComprobante);
        clave.append(codigoNumerico);
        clave.append(tipoEmision);
        int verificador = this.generaDigitoModulo11(clave.toString());
        clave.append(verificador);
        this.claveGenerada = clave.toString();
        if (clave.toString().length() != 49) {
            this.claveGenerada = null;
        }

        return this.claveGenerada;
    }

    public String generaClaveContingencia(Date fechaEmision, String tipoComprobante, String clavesContigencia, String tipoEmision) throws InputMismatchException {
        //int verificador = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fecha = dateFormat.format(fechaEmision);
        StringBuilder clave = new StringBuilder(fecha);
        clave.append(tipoComprobante);
        clave.append(clavesContigencia);
        clave.append(tipoEmision);
        int verificador = this.generaDigitoModulo11(clave.toString());
        if (verificador != 10) {
            clave.append(verificador);
            this.claveGenerada = clave.toString();
        }

        if (clave.toString().length() != 49) {
            this.claveGenerada = null;
        }

        return this.claveGenerada;
    }

    public int generaDigitoModulo11(String cadena) {
        int baseMultiplicador = 7;
        // out.println("CADENA-->" + cadena);
        int[] aux = new int[cadena.length()];
        int multiplicador = 2;
        int total = 0;
        //int verificador = false;

        for(int i = aux.length - 1; i >= 0; --i) {
            aux[i] = Integer.parseInt("" + cadena.charAt(i));
            aux[i] *= multiplicador;
            ++multiplicador;
            if (multiplicador > baseMultiplicador) {
                multiplicador = 2;
            }

            total += aux[i];
        }

        int verificador;
        if (total != 0 && total != 1) {
            verificador = 11 - total % 11 == 11 ? 0 : 11 - total % 11;
        } else {
            verificador = 0;
        }

        if (verificador == 10) {
            verificador = 1;
        }

        return verificador;
    }

    public String getClaveGenerada() {
        return this.claveGenerada;
    }

    public void setClaveGenerada(String claveGenerada) {
        this.claveGenerada = claveGenerada;
    }
}
