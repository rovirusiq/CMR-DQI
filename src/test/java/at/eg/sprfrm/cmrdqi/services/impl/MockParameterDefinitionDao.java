package at.eg.sprfrm.cmrdqi.services.impl;

import at.eg.sprfrm.cmrdqi.dao.IDqiDefinitionParameterDao;
import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameter;

import java.util.ArrayList;
import java.util.List;

public class MockParameterDefinitionDao implements IDqiDefinitionParameterDao{

	
	private List<DqiDefinitionParameter> lst;
	
	public static class Builder {
		
		private List<DqiDefinitionParameter> lst=new ArrayList<DqiDefinitionParameter>();
		
		public Builder add(DqiDefinitionParameter param) {
			this.lst.add(param);
			return this;
		}
		
		public MockParameterDefinitionDao build() {
			return new MockParameterDefinitionDao(this.lst);
		}
	}

	/************************************************************************************************************
	 *
	 *Constructors
	 *
	 ************************************************************************************************************/
	private MockParameterDefinitionDao(List<DqiDefinitionParameter> list) {
		this.lst=list;
	}

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
	@Override
	public List<DqiDefinitionParameter> selectAllParameters() {
		return this.lst;
	}
}
