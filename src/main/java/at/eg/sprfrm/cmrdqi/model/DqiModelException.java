package at.eg.sprfrm.cmrdqi.model;

import at.eg.sprfrm.cmrdqi.DqiException;

public class DqiModelException extends DqiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1471839413761290619L;

	public DqiModelException(String message) {
		super(message);
	}

	public DqiModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public DqiModelException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
