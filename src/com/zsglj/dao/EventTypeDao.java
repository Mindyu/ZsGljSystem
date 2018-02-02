package com.zsglj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zsglj.util.ConnectSQL;

public class EventTypeDao {

	public static ResultSet queryAllEventType(){
		ResultSet rs = null;
		Connection conn = ConnectSQL.getConnection();
		String sql = "select TypeID,TypeName from glj_eventType where ExistStatus = '1'";
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			rs = pstate.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void main(String[] args) {
		EventTypeDao e = new EventTypeDao();
		ResultSet rs = e.queryAllEventType();
		try {
			while( rs!=null && rs.next()){
				System.out.println(rs.getString("TypeID"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
