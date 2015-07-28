package at.eg.sprfrm.cmrdqi.testutil;

import java.io.Reader;
import java.util.Date;

import javax.sql.rowset.serial.SerialClob;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class TestJdbcUtils {
	
	public static final Date convertOracleTimestampToDate(SqlRowSet rowSet,String columnlabel) {
		java.util.Date rsp=null;
		try {
			if ((rowSet.getObject(columnlabel)!=null) && (rowSet.getObject(columnlabel) instanceof oracle.sql.TIMESTAMP)) {
				oracle.sql.TIMESTAMP tmstmp=(oracle.sql.TIMESTAMP)rowSet.getObject(columnlabel);
				rsp=new java.util.Date(tmstmp.dateValue().getTime());
			
			} else {
				rsp=new Date(rowSet.getTimestamp(columnlabel).getTime());
			}
		} catch (Exception ex) {
			throw new RuntimeException("Cannot convert oracle.sql.TIMESTAMP to java.util.Date",ex);
		}
		return rsp;
	}
	public static final String convertClobToString(SqlRowSet rowSet,String columnlabel) {
		SerialClob clob=(javax.sql.rowset.serial.SerialClob)rowSet.getObject(columnlabel);
		StringBuffer rsp=new StringBuffer();
		char[] substream=new char[100];
		try {
			Reader rdr=clob.getCharacterStream();
			while(rdr.read(substream)!=-1) {
				rsp.append(substream);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Cannot convert SerialClob to String",ex);
		}
		return rsp.toString().trim();
	}

}
