package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.services.DqiServiceException;

public class DefinitionParseException extends DqiServiceException implements ISimpleParameterParserMarkers {
	
	private char offendingChar=EMPTY_CHAR;
	private int position=-1;

	public DefinitionParseException(char offending, int position,String arg0) {
		super(arg0);
		this.offendingChar=offending;
		this.position=position;
	}
	
	

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DefinitionParseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the offendingChar
	 */
	public char getOffendingChar() {
		return offendingChar;
	}



	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
	
	

}
