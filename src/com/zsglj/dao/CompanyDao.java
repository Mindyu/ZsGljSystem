package com.zsglj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zsglj.util.ConnectSQL;

public class CompanyDao {
	
	public static ResultSet queryAllCompanies(){
		ResultSet rs = null;
		Connection conn = ConnectSQL.getConnection();
		String sql = "select CompanyID,CompanyName from glj_company where ExistStatus = '1'";
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static String queryCompanyByID(String id){
		String str = "";
		Connection conn = ConnectSQL.getConnection();
		String sql = "select CompanyName from glj_company where CompanyID = '"+id+"'";
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			ResultSet rs = pstate.executeQuery();
			if (rs.next()) {
				str = rs.getString("CompanyName");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
}
