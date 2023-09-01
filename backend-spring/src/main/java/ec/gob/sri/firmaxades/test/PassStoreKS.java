package ec.gob.sri.firmaxades.test;

import es.mityc.javasign.pkstore.IPassStoreKS;

import java.security.cert.X509Certificate;

class PassStoreKS implements IPassStoreKS {

	/** Contraseña de acceso al almacén. */
	private transient String password;

	/**
	 * <p>Crea una instancia con la contraseña que se utilizará con el almacén relacionado.</p>
	 * @param pass Contraseña del almacén
	 */
	PassStoreKS(final String pass) {
		this.password = new String(pass);
	}

	/**
	 * <p>Devuelve la contraseña configurada para este almacén.</p>
	 * @param certificate No se utiliza
	 * @param alias no se utiliza
	 * @return contraseña configurada para este almacén
	 * @see IPassStoreKS#getPassword(X509Certificate, String)
	 */
	public char[] getPassword(final X509Certificate certificate, final String alias) {
		return password.toCharArray();
	}

}
