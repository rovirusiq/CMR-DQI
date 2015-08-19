package at.eg.sprfrm.cmrdqi.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleParameterParserState implements ISimpleParameterParserMarkers{
	
	@SuppressWarnings("all")
	private Logger log=LoggerFactory.getLogger(SimpleParameterParserState.class);
	private int position=-1;
	private Pattern paramNamePattern=Pattern.compile("^[a-z]|[0-9]|_|-|$", Pattern.CASE_INSENSITIVE);
	private static final List<String> MARKER_CHARACTERS=Arrays.asList(""+ESCAPE_CHAR,""+PARAM_MARKER_START_1,""+PARAM_MARKER_START_2,""+PARAM_MARKER_END);
	
	
	//0-ecaping was not started, 1- escaping was started
	private int escapingProcessingState=0; 
	
	/*
	 * 0- no marker found
	 * 1 - first start marker was identified,
	 * 2 -second start marker was identified
	 */
	private int paramProcessingState=0;
	
	
	
	
	protected SimpleParameterParserState() {
		
	}
	
	protected SimpleParameterParserState(int escapeState,int paramState) {
		this.escapingProcessingState=escapeState;
		this.paramProcessingState=paramState;
	}
	
	public boolean isEscapingOn() {
		return (escapingProcessingState==1);
	}
	
	public boolean isParamProcessingOn() {
		return (paramProcessingState!=0);
	}
	
	private int getEscapeState() {
		return this.escapingProcessingState;
	}
	
	private int getParamState() {
		return this.paramProcessingState;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	private boolean isCommonCharacterValidInCurrentState(char c) {
		if (paramProcessingState==2) {
			return paramNamePattern.matcher(""+c).matches();
		} else if (paramProcessingState==1) {
			return (c==PARAM_MARKER_START_2);
		}
		return true;
	}
	
	private void changeInternalStatus(int newEscapingState,int newParamState) {
		escapingProcessingState=newEscapingState;
		paramProcessingState=newParamState;
	}
	
	private RuntimeException giftWrapppedException(char toProcess) {
		throw new DefinitionParseException(toProcess,position,"Invalid character["+toProcess+"] in current state:"+this);
	}
	
	/**
	 * @param toProcess Character to be processed
	 * @return One of the values for ParserEventType
	 * <ul>
	 *  <li>CHARACTER_PROCESSED - the character can be safely added to the output, taking into account previous events</li>
	 * 	<li>CHARACTER_IGNORED - The character must be ignored. This means is one of the special characters and an update was performed on the internal status</li>
	 *  <li>PARAMETER_STARTED - The character must be ignored and the Parser should prepare to &quot;collect&quot; a parameter name</li>
	 *  <li>PARAMETER_ENDED - The character must be ignored and the Parse is informed that the parameter name is complete</li>
	 * 			
	 * @throws DefinitionParseException When an offending character is found.
	 * 		The exception object offers information about offending character, position in the input and internal state of the parsing;
	 */
	/**
	 * @param toProcess
	 * @return
	 * @throws DefinitionParseException
	 */
	public ParserEventType apply(char toProcess) throws DefinitionParseException {
		
		this.position++;
		
		if ( (isEscapingOn()) || (!MARKER_CHARACTERS.contains(""+toProcess))) {	
			if (isCommonCharacterValidInCurrentState(toProcess)) {
				if (isEscapingOn()) {
					this.changeInternalStatus(0, this.getParamState());
				}
				return ParserEventType.CHARACTER_PROCESSED;
			} else {
				throw giftWrapppedException(toProcess);
			}
		}	
		/*
		 * we encounter ESCAPE_CHAR and we know escape is off (we have validated this previous)
		 * ****we modify internal status and return CHARACTER_IGNORED
		 */
		if (ESCAPE_CHAR==toProcess) {
			if (this.getParamState()==1) {
				throw new DefinitionParseException(toProcess, this.position,"Invalid character["+toProcess+"] in current state:"+this);
			} else {
				this.changeInternalStatus(1, this.getParamState());
				return ParserEventType.CHARACTER_IGNORED;
			}
		}
		
		/*
		 *we encounter PARAM_MARKER_START_1 and we know escape is off (we have validated this previous)
		 * ***if the internal status is right 
		 * ******we change the internal status 
		 * ******and return CHARACTER_IGNORED event
		 * ***else throw exception
		 */
		if (PARAM_MARKER_START_1==toProcess) {
			if (isEscapingOn()) {
				if (isCommonCharacterValidInCurrentState(toProcess)) {
					this.changeInternalStatus(0, this.getParamState());
					return ParserEventType.CHARACTER_PROCESSED;
				} else {
					throw giftWrapppedException(toProcess);
				}
			} else {
				if (this.getParamState()!=0) {
					throw new DefinitionParseException(toProcess,position,
						"Invalid character["+toProcess+"] in current state:"+this);
				}
				this.changeInternalStatus(this.getEscapeState(), 1);
				return ParserEventType.CHARACTER_IGNORED;
				
			}
		}
		
		/*
		 *we encounter PARAM_MARKER_START_2 and we know escape is off (we have validated this previous)
		 * ****we validate the internal state to see if it is allowed din the context 
		 * ****and then we update the internal state and return CHARACTER_IGNORED.
		 */
		if (PARAM_MARKER_START_2==toProcess) {
			if (this.getParamState()!=1) {
				throw new DefinitionParseException(toProcess,position,
					"Invalid character["+toProcess+"] in current state:"+this);
			}
			this.changeInternalStatus(this.getEscapeState(), 2);
			return ParserEventType.PARAMETER_STARTED;
		}
		/*
		 * we encountered PARAM_MARKER_END and we know escape is off (we have validated this previous)
		 * ***change internal status, to mark the end of the parameter, and return character ignored
		 */
		if (PARAM_MARKER_END==toProcess) {
			if (this.getParamState()!=2) {
				throw new DefinitionParseException(toProcess,position,
						"Invalid character["+toProcess+"] in current state:"+this);
			} else {
				this.changeInternalStatus(this.getEscapeState(), 0);
				return ParserEventType.PARAMETER_ENDED;
			}
		}
		
		throw new DefinitionParseException(toProcess, this.position,"Unidentified operation for the character["+toProcess+"] in current state:"+this);
		
	}
	
	
	
	/**
	 * Must be called when there is nothing more to parse.
	 * It will check the internal status and throw an error if something is wrong.
	 * @throws DefinitionParseException
	 * <ul>
	 * 		<li>escaped character is still expected</li>
	 * 		<li>parameter name identification is not complete</li>
	 * </ul>
	 */
	public final void finish() throws DefinitionParseException{
		if (isEscapingOn()) throw new DefinitionParseException(EMPTY_CHAR,this.position,"Waiting for escaped character at position["+this.getPosition()+"]:"+this);
		if (isParamProcessingOn()) throw new DefinitionParseException(EMPTY_CHAR,this.position,"Parameter name not ended at position["+this.getPosition()+"]:"+this);
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleParameterParserState [position=" + position
				+ ", escapingProcessingState=" + escapingProcessingState
				+ ", paramProcessingState=" + paramProcessingState + "]";
	}
	
	

}
