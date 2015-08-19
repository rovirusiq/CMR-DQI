package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.services.DqiServiceException;
import at.eg.sprfrm.cmrdqi.services.IParameterProvider;
import at.eg.sprfrm.cmrdqi.services.InvalidDefinitionParameterException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParseResult {
	
	private final String input;
	private Boolean hasParameters=false;
	private final List<ParserNode> list;
	/**
	 * @param input
	 * @param hasParameters
	 */
	public ParseResult(String originalDefinitions,List<ParserNode> parsingResultList) {
		super();
		this.input = originalDefinitions;
		if (parsingResultList!=null) {
			this.list=parsingResultList;
			for (Iterator<ParserNode> iterator = list.iterator(); iterator.hasNext() && !hasParameters;) {
				
				ParserNode parserNode = iterator.next();
				hasParameters=parserNode.getType()==ParserNodeType.PARAMETER;
				
			}
		} else {
			this.list=new ArrayList<ParserNode>();
		}
	}
	
	/************************************************************************************************************
	 *
	 * Getters and Setters
	 *
	 ************************************************************************************************************/
	/**
	 * @return the input, the original definition of the check.
	 */
	public String getInput() {
		return input;
	}
	
	
	/**
	 * @return the hasParameters
	 */
	public Boolean hasParameters() {
		return hasParameters;
	}

	/************************************************************************************************************
	 *
	 * Public Methods
	 *
	 ************************************************************************************************************/
	public List<String> getParameterNames(){
		List<String> paramNames=new ArrayList<String>();
		if (hasParameters()) {
			for (Iterator<ParserNode> iterator = list.iterator(); iterator.hasNext();) {
				ParserNode parserNode = iterator.next();
				if (parserNode.getType()==ParserNodeType.PARAMETER) {
					paramNames.add(parserNode.getContent());
				}
				
			}
		}
		return paramNames;
	}
	
	public String getActualDefinition(IParameterProvider parameterProvider) throws InvalidDefinitionParameterException,DqiServiceException {
		StringBuffer rsp=new StringBuffer();
		
		for (Iterator<ParserNode> iterator = list.iterator(); iterator.hasNext();) {
			ParserNode parserNode = iterator.next();
			if (parserNode.getType()==ParserNodeType.TEXT) {
				rsp.append(parserNode.getContent());
			} else if (parserNode.getType()==ParserNodeType.PARAMETER){
				rsp.append(parameterProvider.getValue(parserNode.getContent()));
			} else {
				throw new DqiServiceException("Invalid parsing node type["+parserNode.getType()+"]");
			}
		}
	
		return rsp.toString();
	}

}
