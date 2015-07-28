package at.eg.sprfrm.cmrdqi.model;

public enum DqiIssueType {
	
	SIMPLE("SIMPLE");
	
	private String code;
	
	DqiIssueType(String code){
		this.code=code;
	}
	
	public final String value() {
		return this.code;
	}
	
	public static final DqiIssueType valueFrom(String code) throws IllegalArgumentException {
		
		if ( (code==null) || ("".equals(code)) ) throw new IllegalArgumentException("Illegal value for Issue Type["+code+"]");
		
		DqiIssueType rsp=null;
		
		for (DqiIssueType it : DqiIssueType.values()) {
			if (it.value().equals(code)) {
				rsp=it;
			}
		}
		
		if (rsp==null) throw new IllegalArgumentException("Illegal value for Issue Type["+code+"]");
		return rsp;
	}

}
