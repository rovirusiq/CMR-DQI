package at.eg.sprfrm.cmrdqi.dao;

import at.eg.sprfrm.cmrdqi.model.DqiDefinitionParameter;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("dqiDefinitionParameterDao")
public interface IDqiDefinitionParameterDao {
	
	public List<DqiDefinitionParameter> selectAllParameters();
}
