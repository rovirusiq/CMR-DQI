package at.eg.sprfrm.cmrdqi.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDqiExecutionStatus {
	
	
	private static final Logger log=LoggerFactory.getLogger(TestDqiExecutionStatus.class);

	@Test
	public void testValue() {
		assertEquals("IN_PROGRESS",DqiExecutionStatusType.IN_PROGRESS.value());
	}

	@Test
	public void testValueFrom() {
		log.info("Start testValueFrom");
		DqiExecutionStatusType st=DqiExecutionStatusType.valueFrom("SUCCESS");
		assertEquals(DqiExecutionStatusType.SUCCESS,st);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValueFromError1() {
		DqiExecutionStatusType st=DqiExecutionStatusType.valueFrom("");
		assertEquals(DqiExecutionStatusType.SUCCESS,st);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValueFromError2() {
		DqiExecutionStatusType st=DqiExecutionStatusType.valueFrom(null);
		assertEquals(DqiExecutionStatusType.SUCCESS,st);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValueFromError3() {
		DqiExecutionStatusType st=DqiExecutionStatusType.valueFrom("CEVA_CE_NU_EXISTS");
		assertEquals(DqiExecutionStatusType.SUCCESS,st);
	}
}
