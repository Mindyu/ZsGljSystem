package com.zsglj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zsglj.util.ConnectSQL;

public class LocationDao {

	public static ResultSet queryAllLocation(){
		ResultSet rs = null;
		Connection conn = ConnectSQL.getConnection();
		String sql = "select TownID,TownName,StreetName from glj_location ";
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
}
