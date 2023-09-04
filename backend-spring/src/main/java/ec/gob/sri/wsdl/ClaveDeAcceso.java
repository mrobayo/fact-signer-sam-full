// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   ClaveDeAcceso.java

package ec.gob.sri.wsdl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

/**
 * Genera la clave de acceso
 *
 * @author mrobayo Dec 30, 2014
 */
public class ClaveDeAcceso {

	public ClaveDeAcceso() {
	}

	public String generaClave(Date fechaEmision, String tipoComprobante,
			String ruc, String ambiente, String serie,
			String numeroComprobante, String codigoNumerico, String tipoEmision) {
		int verificador = 0;
		if (ruc != null && ruc.length() < 13)
			ruc = String.format("%013d", new Object[] { ruc });

		String fecha = dateFormat.format(fechaEmision);
		StringBuilder clave = new StringBuilder(fecha);
		clave.append(tipoComprobante);
		clave.append(ruc);
		clave.append(ambiente);
		clave.append(serie);
		clave.append(numeroComprobante);
		clave.append(codigoNumerico);
		clave.append(tipoEmision);
		verificador = generaDigitoModulo11(clave.toString());
		clave.append(Integer.valueOf(verificador));
		claveGenerada = clave.toString();

		if (clave.toString().length() != 49) {
			claveGenerada = null;
		}
		return claveGenerada;
	}

	public String generaClaveContingencia(Date fechaEmision,
			String tipoComprobante, String clavesContigencia, String tipoEmision)
			throws InputMismatchException {
		int verificador = 0;
		//SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String fecha = dateFormat.format(fechaEmision);
		StringBuilder clave = new StringBuilder(fecha);
		clave.append(tipoComprobante);
		clave.append(clavesContigencia);
		clave.append(tipoEmision);
		verificador = generaDigitoModulo11(clave.toString());
		if (verificador != 10) {
			clave.append(Integer.valueOf(verificador));
			claveGenerada = clave.toString();
		}
		if (clave.toString().length() != 49)
			claveGenerada = null;
		return claveGenerada;
	}

	/**
	 * Calcula el modulo 11
	 */
	private int generaDigitoModulo11(String cadena) {
		int baseMultiplicador = 7;
		//System.out.println((new StringBuilder()).append("CADENA-->")
		//		.append(cadena).toString());
		int aux[] = new int[cadena.length()];
		int multiplicador = 2;
		int total = 0;
		int verificador = 0;
		for (int i = aux.length - 1; i >= 0; i--) {
			aux[i] = Integer.parseInt((new StringBuilder()).append("")
					.append(cadena.charAt(i)).toString());
			aux[i] = aux[i] * multiplicador;
			if (++multiplicador > baseMultiplicador)
				multiplicador = 2;
			total += aux[i];
		}

		if (total == 0 || total == 1)
			verificador = 0;
		else
			verificador = 11 - total % 11 != 11 ? 11 - total % 11 : 0;
		if (verificador == 10)
			verificador = 1;
		return verificador;
	}

	public String getClaveGenerada() {
		return claveGenerada;
	}

	private String claveGenerada;
	final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
}
