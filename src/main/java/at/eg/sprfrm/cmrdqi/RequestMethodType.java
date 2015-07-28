package at.eg.sprfrm.cmrdqi;


public enum RequestMethodType {
	
	
	CMD_LINE("CMDLN"),
	JAVA_API("JAPI");
	
	private final String code;
	
	RequestMethodType(String code){
		this.code=code;
		
	}
	
	public String value() {
		return this.code;
	}
	
	public static final RequestMethodType valueFrom(String status) throws IllegalArgumentException {
		
		if ( (status==null) || ("".equals(status)) ) throw new IllegalArgumentException("Illegal value for Execution Status["+status+"]");
		
		RequestMethodType rsp=null;
		
		for (RequestMethodType st : RequestMethodType.values()) {
			if (st.value().equals(status)) {
				rsp=st;
			}
		}
		
		if (rsp==null) throw new IllegalArgumentException("Illegal value for Execution Status["+status+"]");
		return rsp;
	}

}
