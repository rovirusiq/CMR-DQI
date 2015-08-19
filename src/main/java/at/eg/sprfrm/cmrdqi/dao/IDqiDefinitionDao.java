package at.eg.sprfrm.cmrdqi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import at.eg.sprfrm.cmrdqi.model.DqiDefinition;

@Component("dqiDefinitionDao")
public interface IDqiDefinitionDao {
	
	
	public DqiDefinition selectDefinition(@Param("id")Long id);	
	
	public List<DqiDefinition> selectAllDefintions();
	public List<DqiDefinition> selectAllDefintions(String area, String group,String subGroup);
	
	public List<Long> selectAllDefintionIds();
	public List<Long> selectAllDefintionIds(String area, String group,String subGroup);

	public Long executeGenericQueryDefinition(DqiDefinition definition);
	
}
