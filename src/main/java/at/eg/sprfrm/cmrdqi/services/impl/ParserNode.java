package at.eg.sprfrm.cmrdqi.services.impl;

public class ParserNode {
	
	private final ParserNodeType type;
	private String content;
	
	protected ParserNode(ParserNodeType designatedType) {
		this.type=designatedType;
	}
	
	protected ParserNode(ParserNodeType designatedType, String content) {
		this.type=designatedType;
		this.content=content;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	

	/**
	 * @return the type
	 */
	public ParserNodeType getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParserNode [type=" + type + ", content=" + content + "]";
	}
	
	

}
