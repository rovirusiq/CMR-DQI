package at.eg.sprfrm.cmrdqi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;

@Component("dqiDefinitionDao")
public interface IDqiDefinitionDao {
	
	
	public DqiDefinition selectDefinition(@Param("id")Long id);	
	
	public List<DqiDefinition> selectAllDefintions();
	
	public List<Long> selectAllDefintionIds();

	public Long executeGenericQueryDefinition(DqiDefinition definition);
	
}
