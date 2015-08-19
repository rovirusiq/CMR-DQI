package at.eg.sprfrm.cmrdqi.model;

public enum DqiDefinitionParameterRefreshFlagType {
	NO("NO");

	private static final String EXCEPTION_MESSAGE_PREFIX = "Illegal value for DqiDefinitionParameterRefreshFlagType";
	private String code;

	DqiDefinitionParameterRefreshFlagType(String code) {
		this.code = code;
	}

	public final String value() {
		return this.code;
	}

	public static final DqiDefinitionParameterRefreshFlagType valueFrom(
			String code) throws IllegalArgumentException {

		if ((code == null) || ("".equals(code)))
			throw new IllegalArgumentException(EXCEPTION_MESSAGE_PREFIX + "["
					+ code + "]");

		DqiDefinitionParameterRefreshFlagType rsp = null;

		for (DqiDefinitionParameterRefreshFlagType it : DqiDefinitionParameterRefreshFlagType
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
