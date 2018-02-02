package com.zsglj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zsglj.util.ConnectSQL;

public class UserDao {

	public static String queryCompanyIdByUserId(String userID){
		String companyID = "";
		Connection conn = ConnectSQL.getConnection();
		String sql = "select CompanyID from glj_user where UserID = '"+userID+"'";
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			ResultSet rs = pstate.executeQuery();
			if (rs.next()) {
				companyID = rs.getString("CompanyID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companyID;
	}
}
