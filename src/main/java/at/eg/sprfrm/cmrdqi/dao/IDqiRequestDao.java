package at.eg.sprfrm.cmrdqi.dao;

import org.springframework.stereotype.Component;

import at.eg.sprfrm.cmrdqi.model.DqiRequest;

@Component("dqiRequestDao")
public interface IDqiRequestDao {
	
	public DqiRequest selectRequestDetails(Long idRequest);
	public Long insertRequest(DqiRequest request);

}
