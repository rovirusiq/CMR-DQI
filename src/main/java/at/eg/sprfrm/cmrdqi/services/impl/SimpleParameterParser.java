package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.services.IDefinitionParser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleParameterParser implements IDefinitionParser,ISimpleParameterParserMarkers{
	
	private static Logger log=LoggerFactory.getLogger(SimpleParameterParser.class);

	@Override
	public ParseResult parseDefinition(String originalDefinition) {
				
		List<ParserNode>parsedList=processStream(originalDefinition);
		ParseResult rslt=new ParseResult(originalDefinition,parsedList);
		return rslt;
	}
	
	
	private ParserNode createNode(ParserNodeType pType) {
		return new ParserNode(pType);
	}
	
	private SimpleParameterParserState createState() {
		return new SimpleParameterParserState();
	}

	protected  List<ParserNode> processStream(String originalDefinition) throws DefinitionParseException{	
		ArrayList<ParserNode> rspLst=new ArrayList<ParserNode>();
		ParserNode currentNode=null;
		SimpleParameterParserState state=createState();
		
		Reader rdr=new StringReader(originalDefinition);
		StringBuffer tempS=new StringBuffer();
		int CHARS_READ=1;
		char[] c=new char[CHARS_READ];
		
		
		try {
			while(rdr.read(c)!=-1) {
				ParserEventType ev=state.apply(c[0]);
				log.info("Event generated after apply:"+ev.value());
				if (ParserEventType.CHARACTER_PROCESSED==ev) {
					if (currentNode==null) {
						currentNode=createNode(ParserNodeType.TEXT);
						tempS=new StringBuffer();
					}
					tempS.append(c[0]);
				} else if (ParserEventType.PARAMETER_STARTED==ev) {
					if (currentNode!=null) {
						currentNode.setContent(tempS.toString());
						rspLst.add(currentNode);
						
					}
					currentNode=createNode(ParserNodeType.PARAMETER);
					tempS=new StringBuffer();
				} else if (ParserEventType.PARAMETER_ENDED==ev) {
					currentNode.setContent(tempS.toString());
					rspLst.add(currentNode);
					currentNode=null;
				}
				//for CHARACTER_IGNORED we don't do anything
			}
			
			state.finish();
			log.info("Finished the parsing");
			if ( (currentNode!=null) && (tempS.length()>0) ){
				currentNode.setContent(tempS.toString());
				rspLst.add(currentNode);
				currentNode=null;
			}
		} catch (IOException ex) {
			throw new DefinitionParseException("An IO exception was generated when parsing the original definition",ex);
		}
		
		return rspLst;
	}
	
	

}
