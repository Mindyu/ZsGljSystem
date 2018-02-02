package com.zsglj.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*****************************************************************
* 类名    :  PageUtil
* 作者    :  王子豪
* 日期    :  2016-08-22
* 修改历史：
* 功能描述: 数据分页显示的公共类
******************************************************************/
public class PageUtil {
	private static String pageutilSQL = null;

	/*****************************************************************
	* 方法名  :  getDataCounts
	* 作者    :  王子豪
	* 日期    :  2016-08-22
	* 输入参数：sql：查询的数据库操作语句
	* 输出参数：int：返回查询到的数据条数
	* 功能描述: 根据形参查询对应数据的条数
	* 修改历史：
	******************************************************************/
	public static int getDataCounts(String sql) {
		int count = 0;
		Connection conn = ConnectSQL.getConnection();
		pageutilSQL = " select * from " + sql;
		ResultSet rs = null;
		PreparedStatement pStatement = null;
		try {
			pStatement = conn.prepareStatement(pageutilSQL);
			rs = pStatement.executeQuery();
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return count;
	}

	/*****************************************************************
	* 方法名  :  getPage
	* 作者    :  王子豪
	* 日期    :  2016-08-22
	* 输入参数：pagenumber：第几页；rows：每页显示多少行；sql：查询的数据库语句
	* 输出参数：ResultSet：返回查询到的数据集合
	* 功能描述: 获取列表分页的数据
	* 修改历史：
	******************************************************************/
	public static ResultSet getPage(int pagenumber, int rows, String sql) {
		Connection conn = ConnectSQL.getConnection();
		int index = 0;
		if (pagenumber - 1 > 0) {
			index = (pagenumber - 1) * rows;
		}

		pageutilSQL = "SELECT * FROM " + sql + " limit " + index + "," + rows;

		ResultSet rs;
		try {
			rs = conn.createStatement().executeQuery(pageutilSQL);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
