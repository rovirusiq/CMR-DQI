package at.eg.sprfrm.cmrdqi;

public class DqiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DqiException(String message) {
		super(message);
	}

	public DqiException(String message, Throwable cause) {
		super(message, cause);
	}

	public DqiException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
