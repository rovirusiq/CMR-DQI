package at.eg.sprfrm.cmrdqi.services.impl;

public enum ParserNodeType {
	TEXT("TEXT"),
	PARAMETER("PARAMETER");

	private static final String EXCEPTION_MESSAGE_PREFIX = "Illegal value for ParserNodeType";
	private String code;

	ParserNodeType(String code) {
		this.code = code;
	}

	public final String value() {
		return this.code;
	}

	public static final ParserNodeType valueFrom(String code)
			throws IllegalArgumentException {

		if ((code == null) || ("".equals(code)))
			throw new IllegalArgumentException(EXCEPTION_MESSAGE_PREFIX + "["
					+ code + "]");

		ParserNodeType rsp = null;

		for (ParserNodeType it : ParserNodeType.values()) {
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
