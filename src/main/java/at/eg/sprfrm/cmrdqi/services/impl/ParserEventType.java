package at.eg.sprfrm.cmrdqi.services.impl;

public enum ParserEventType {
	
	CHARACTER_PROCESSED("CHARACTER_PROCESSED"),
	CHARACTER_IGNORED("CHARACTER_IGNORED"),
	PARAMETER_STARTED("PARAMETER_STARTED"),
	PARAMETER_ENDED("PAARAMETER_ENDED");
	
	private static final String EXCEPTION_MESSAGE_PREFIX="Illegal value for ParserEventType";
	private String code;
	
	ParserEventType(String code){
		this.code=code;
	}
	
	public final String value() {
		return this.code;
	}
	
	public static final ParserEventType valueFrom(String code) throws IllegalArgumentException {
		
		if ( (code==null) || ("".equals(code)) ) throw new IllegalArgumentException(EXCEPTION_MESSAGE_PREFIX+"["+code+"]");
		
		ParserEventType rsp=null;
		
		for (ParserEventType it : ParserEventType.values()) {
			if (it.value().equals(code)) {
				rsp=it;
			}
		}
		
		if (rsp==null) throw new IllegalArgumentException(EXCEPTION_MESSAGE_PREFIX+"["+code+"]");
		return rsp;
	}
}
