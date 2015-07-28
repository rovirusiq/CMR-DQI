package at.eg.sprfrm.cmrdqi.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import at.eg.sprfrm.cmrdqi.model.DqiIssue;
import at.eg.sprfrm.cmrdqi.model.DqiModelException;
import at.eg.sprfrm.cmrdqi.model.DqiSimpleIssue;

public class TestDqiIssue {

	@Test
	public void testDqiIssueConstructorWithNullAsType() {
		DqiIssue iss=new DqiSimpleIssue(null,"Create by JUNIT");
		assertEquals("SIMPLE",iss.getType());
	}
	

	@Test
	public void testDqiIssueConstructorWithEmptyStringAsType() {
		DqiIssue iss=new DqiSimpleIssue("","Create by JUNIT");
		assertEquals("SIMPLE",iss.getType());
	}
	
	@Test
	public void testDqiIssueConstructorWithSimpleAsType() {
		DqiIssue iss=new DqiSimpleIssue("SIMPLE","Create by JUNIT");
		assertEquals("SIMPLE",iss.getType());
	}

	
	@Test(expected=DqiModelException.class)
	@SuppressWarnings({ "all", "unused" })
	public void testDqiIssueConstructorWithDifferenThanSimpleAsType() {
		DqiIssue iss=new DqiSimpleIssue("SIMPLE-OTHER","Create by JUNIT");
	}
}
