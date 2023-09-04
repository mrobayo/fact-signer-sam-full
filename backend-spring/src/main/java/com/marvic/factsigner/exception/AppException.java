package com.marvic.factsigner.exception;

/**
 *
 * @author hrobayo Jun 15, 2010
 */
public class AppException extends RuntimeException {

	/**
	 *
	 */
	public AppException(){
		super();
	}

	public AppException(String msg, Object... params){
		super((msg != null) ? String.format(msg, params) : null);
	}

	public AppException(Exception e, String msg, Object... params) {
		super((msg != null) ? String.format(msg, params) : null, e);
	}

	public AppException(Exception e){
		super(e.getMessage());
	}

	public AppException(String msg, String argument){
		super(String.format(msg, argument));
	}

	private static final long serialVersionUID = 7805393878607788241L;
}
