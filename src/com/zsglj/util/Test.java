package com.zsglj.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String sql = "SELECT * FROM glj_event WHERE CompanyID= '" + "1422db03-d2c2-461f-ba67-75e04032f777" + "'";
    	PreparedStatement pstate;
		try {
			pstate = ConnectSQL.getConnection().prepareStatement(sql);
			ResultSet rs = pstate.executeQuery();
	    	if (rs!=null) {
	    		while (rs.next()) {
	    			System.out.println("result:"+rs.getString("EventDate"));
					
				}
	    	}
	    	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
	}

}
