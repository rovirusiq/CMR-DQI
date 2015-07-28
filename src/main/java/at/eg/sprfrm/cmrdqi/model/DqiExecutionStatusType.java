package at.eg.sprfrm.cmrdqi.model;

public enum DqiExecutionStatusType {
	
	SUBMITTED("SUBMITTED"),
	IN_PROGRESS("IN_PROGRESS"),
	SUCCESS("SUCCESS"),
	EXCEPTION("EXCEPTION"),
	ERROR("ERROR");
	
	private final String code;
	
	DqiExecutionStatusType(String code){
		this.code=code;
		
	}
	
	public String value() {
		return this.code;
	}
	
	public static final DqiExecutionStatusType valueFrom(String status) throws IllegalArgumentException {
		
		if ( (status==null) || ("".equals(status)) ) throw new IllegalArgumentException("Illegal value for Execution Status["+status+"]");
		
		DqiExecutionStatusType rsp=null;
		
		for (DqiExecutionStatusType st : DqiExecutionStatusType.values()) {
			if (st.value().equals(status)) {
				rsp=st;
			}
		}
		
		if (rsp==null) throw new IllegalArgumentException("Illegal value for Execution Status["+status+"]");
		return rsp;
	}

}
