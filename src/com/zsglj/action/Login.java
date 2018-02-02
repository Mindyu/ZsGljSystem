package com.zsglj.action;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zsglj.util.ConnectSQL;

@SuppressWarnings("serial")
public class Login extends ActionSupport implements SessionAware {

	private String userID;
	private int userType = -1;
	private String userName; 		// 用户名
	private String password; 		// 用户密码
	@SuppressWarnings("rawtypes")
	private Map session; 			// session
	private Connection conn;
	private PreparedStatement pstmt;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {

		this.password = password;

	}

	public void setSession(@SuppressWarnings("rawtypes") Map session) {
		this.session = session;
	}

	private HttpServletResponse response = ServletActionContext.getResponse();

	public Login() {
		System.out.println("用户登陆!!");
		conn = ConnectSQL.getConnection();
	}

	/*****************************************************************
	 * 方法名 : execute 作者 : 冯健 日期 : 2016-09-20 输入参数： 输出参数：null/LoginFail 功能描述:
	 * 连接数据库验证用户名ID和密码，判断权限返回不同的action。登陆成功返回上一级页面，函数返回null；登陆失败返回“LoginFail”。
	 * 返回LoginFail重新加载登录界面 修改历史：
	 ******************************************************************/
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		try {
			// 加载数据库
			conn = ConnectSQL.getConnection();
			Statement sta = conn.createStatement();

			String sql = "SELECT UserID,Password,UserType FROM glj_user WHERE UserName='"
					+ userName + "' AND ExistStatus=1  AND UserType IN (1,2,3)";
			ResultSet rs = sta.executeQuery(sql);
			// 验证用户名，通过则从数据库取出用户数据
			if (rs != null && rs.next()) {
				if(!password.equals(rs.getString("password"))){
					PrintWriter pw = response.getWriter();
					pw.write("<script language='javascript'>alert('密码输入有误'); location.href='/ZsGljSystem/pageAreas/login.jsp'</script>");
					return null;
				}
				userID = rs.getString("userId");
				userType = rs.getInt("usertype");
			} else {
				PrintWriter pw = response.getWriter();
				pw.write("<script language='javascript'>alert('用户名不存在'); location.href='/ZsGljSystem/pageAreas/login.jsp'</script>");
				return null;
			}
			this.session.put("UserID", userID);
			this.session.put("UserName", userName);
			this.session.put("UserType", userType);
			if (userType==1) {      //管理员登陆
				return "adminloginsuccess";
			}
			// 测试session已存取
			/*
			 * if (session.get("UserID") != null) {
			 * System.out.println("以下为测试session已存取");
			 * System.out.println(session.get("UserID"));
			 * System.out.println(session.get("UserName"));
			 * System.out.println(session.get("UserType")); }
			 */
			// 验证成功
			return SUCCESS;

		} catch (Exception e) {
			System.out.println("数据库连接失败");
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		// 验证失败，返回登录界面重新登录
		return INPUT;
	}

	// 退出登录的方法
	public String logout() throws Exception {
		@SuppressWarnings("rawtypes")
		Map session1 = ActionContext.getContext().getSession();
		session1.clear();
		// System.out.println("LogoutSessionUserName="+session.get("UserName"));
		return SUCCESS;
	}

	// 推出整个系统
	public String systemQuit() {
		@SuppressWarnings("rawtypes")
		Map session1 = ActionContext.getContext().getSession();
		session1.clear();
		ConnectSQL.closeconn();
		return null;
	}
}