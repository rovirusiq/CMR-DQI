package at.eg.sprfrm.cmrdqi.services;

import at.eg.sprfrm.cmrdqi.DqiException;

public class DqiServiceException extends DqiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DqiServiceException(String arg0) {
		super(arg0);
	}

	public DqiServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
