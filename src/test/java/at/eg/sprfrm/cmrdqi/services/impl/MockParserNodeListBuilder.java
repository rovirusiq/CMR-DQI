package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.services.impl.ParserNode;
import at.eg.sprfrm.cmrdqi.services.impl.ParserNodeType;

import java.util.ArrayList;
import java.util.List;

public class MockParserNodeListBuilder {
	
	private List<ParserNode> lst=new ArrayList<ParserNode>();
	
	/************************************************************************************************************
	 *
	 *Constructors
	 *
	 ************************************************************************************************************/

	/************************************************************************************************************
	 *
	 *Getters and Setters
	 *
	 ************************************************************************************************************/

	/************************************************************************************************************
	 *
	 *Internal Methods
	 *
	 ************************************************************************************************************/

	/************************************************************************************************************
	 *
	 *Public Exposed Methods
	 *
	 ************************************************************************************************************/
	public MockParserNodeListBuilder add(ParserNodeType type, String content) {
		return this.add(new ParserNode(type,content));
	}
	
	public MockParserNodeListBuilder add(ParserNode node) {
		this.lst.add(node);
		return this;
	}
	
	public List<ParserNode> build(){
		List<ParserNode> rsp= new ArrayList<ParserNode>(this.lst);
		this.lst=new ArrayList<ParserNode>();
		return rsp;
	}
}
