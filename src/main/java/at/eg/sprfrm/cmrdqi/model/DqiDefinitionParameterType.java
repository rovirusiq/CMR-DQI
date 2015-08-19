package at.eg.sprfrm.cmrdqi.model;

public enum DqiDefinitionParameterType {
	
	FIXED("Fixed");

	private static final String EXCEPTION_MESSAGE_PREFIX = "Illegal value for DqiDefinitionParameterType";
	private String code;

	DqiDefinitionParameterType(String code) {
		this.code = code;
	}

	public final String value() {
		return this.code;
	}

	public static final DqiDefinitionParameterType valueFrom(String code)
			throws IllegalArgumentException {

		if ((code == null) || ("".equals(code)))
			throw new IllegalArgumentException(EXCEPTION_MESSAGE_PREFIX + "["
					+ code + "]");

		DqiDefinitionParameterType rsp = null;

		for (DqiDefinitionParameterType it : DqiDefinitionParameterType
				.values()) {
			if (it.value().equals(code)) {
				rsp = it;
			}
		}

		if (rsp == null)
			throw new IllegalArgumentException(EXCEPTION_MESSAGE_PREFIX + "["
					+ code + "]");
		return rsp;
	}
}
