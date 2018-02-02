package com.zsglj.admin.actions.user;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zsglj.dao.CompanyDao;
import com.zsglj.dao.SqlOperate;

public class UserOperation extends ActionSupport{

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map session = ActionContext.getContext().getSession();
	private SqlOperate sqlOperate;

	private String usersql;
	private String userName;
	private int userTypevalue;
	private String telephone;
	private String company;
	private String remark;
	private String userintroduction;
	private String userId;
	private int status;
	private String approverName;
	private String approveTime;
	private String password;
	private String result;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	private String uploadContentType;  //上传文件名
	
	private String uploadFileName; //上传文件类型
	
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
	public UserOperation(){
		request = ServletActionContext.getRequest();
		sqlOperate = new SqlOperate();
		}
	
	/****************************************************************
	 * 方法：checkUserName
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：当添加或更新用户时，该方法获取输入的用户名，如果用户名已存在于数据库中，那么将给出提示
	 * 修改历史：
	 ****************************************************************/
	
	//检查用户是否重名
	public String checkUserName() throws UnsupportedEncodingException {
		HttpServletResponse response = ServletActionContext.getResponse(); 
		response.setContentType("text/plain;charset=UTF-8"); 
		userName = request.getParameter("username");
		try {
			userName= URLDecoder.decode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("添加的用户名为："+userName);
		if (sqlOperate.login()) {
			usersql = "select UserName from glj_user where UserName = '"+userName+"'";
			String rs = sqlOperate.queryDataReturnStr(usersql);
			if(rs != null )
			{
				System.out.println("用户名已存在！");
				try { 
				     response.getWriter().write("false"); 
				} catch (IOException e) { 
					e.printStackTrace(); 
				} 
				return null;
			}
		}
		return null;
	}
	/***************************************************************************
	 * 方法：addUser
	 * 作者：李雪莹
	 * 日期：2016-08-24
	 * 输入参数：null
	 * 输出参数：RESULT :执行action，跳转至/tomatoAreas/Admin/UserManage/userInfo.jsp
	 * 功能描述：当点击添加按钮，在输入用户信息后提交时，通过action匹配到该方法，该方法得到用户所有信息，插入数据库中
	 * 修改历史：
	 ***************************************************************************/
	//添加用户
	public String addUser() {
		userId = request.getParameter("UserId");
		String userType = session.get("UserType").toString();
		request.setAttribute("userType",userType);
		if(userType.equals("3") ||userType.equals("2")  ){
			request.setAttribute("addBtnType","hidden");
		}else if(userType.equals("1")){
			request.setAttribute("addBtnType","image");
		}
		userName = request.getParameter("UserName");
		userTypevalue =Integer.parseInt(request.getParameter("UserType"));
		userintroduction = request.getParameter("userintroduction");
		company = request.getParameter("Company");
		password = request.getParameter("password");
		telephone = request.getParameter("Telephone");
		remark = request.getParameter("Remark");
		
		//防止单引号问题
		userintroduction = userintroduction.replaceAll("'", "''");
		remark = remark.replaceAll("'", "''");
		company = company.replaceAll("'", "''");
		if (sqlOperate.login()) {
			usersql = "INSERT INTO glj_user(UserID,UserName,UserType,Password,CompanyID,Telephone,ExistStatus,UserIntroduction,Remark) VALUES('"
					+userId+"','"+ userName+ "','"+ userTypevalue+ "','"+password+"','"
					+company+"','"+telephone+"','"+1+"','"+userintroduction+"','"+remark+"')";
			int flag = sqlOperate.executeData(usersql);
			if (flag == 0) {
				request.setAttribute("result", "addFail");
				return "RESULT";
			} else if (flag == 1) {
				request.setAttribute("result", "addSuccess");
			}
		} else {
			System.err.println("数据库连接失败");
			return "RESULT";
		}
		return "RESULT";
	}
	
	/***********************************************************************************************
	 * 方法：putupdateUser
	 * 作者：李雪莹
	 * 日期：2016-08-24
	 * 输入参数：null
	 * 输出参数：RESULT :执行action，跳转至/tomatoAreas/Admin/UserManage/updateUser.jsp
	 * 功能描述：在点击添加或者更新按钮时，首先找到该方法。如果是添加用户，那么将用户信息置为空。如果是更新用户，那么从数据库中取出该用户信息并显示出来。
	 * 修改历史：
	 ***********************************************************************************************/
	//更新用户，添加和更新公用函数
	@SuppressWarnings("null")
	public String putupdateUser(){
		userId = request.getParameter("userId");
	    status = -1;
		userName = "";
		userTypevalue=0;
		userintroduction = "";
		password =  "";
		company = "";
		telephone = "";
		remark = "";
		String  typeValue1 = "", typeValue2 = "", typeValue3 = "";
		String statusValue0 = "",statusValue1 = "";
		String action = "actionsUser/addUser";
		String jspTitle = "添加用户";
		if (userId != null) {
			jspTitle = "更新用户";
			action = "actionsUser/updateUser";
			sqlOperate.login();
			usersql = "SELECT * FROM glj_user,glj_company WHERE glj_user.CompanyID = glj_company.CompanyID "
					+"AND glj_user.UserID= '"+ userId + "'";
			try {
				ResultSet rs = sqlOperate.queryDataReturnRS(usersql);
				while (rs!=null && rs.next()) {
					userName = rs.getString("UserName");
					userTypevalue = rs.getInt("UserType");
					status = rs.getInt("ExistStatus");
					password = rs.getString("Password");
					company = rs.getString("glj_user.CompanyID");
					telephone = rs.getString("Telephone");
					remark = rs.getString("Remark");
					userintroduction = rs.getString("UserIntroduction");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (userTypevalue == 1) {
				typeValue1 = "selected=\"selected\"";
			} else if (userTypevalue == 2) {
				typeValue2 = "selected=\"selected\"";
			}else if (userTypevalue == 3) {
				typeValue3 = "selected=\"selected\"";
			}
			if (status == 1) {
				statusValue1 = "selected=\"selected\"";
			} else if (status == 0) {
				statusValue0 = "selected=\"selected\"";
			}
		}else{
			userId = UUID.randomUUID().toString();
		}
		request.setAttribute("userId", userId);
		request.setAttribute("userName",userName);
		request.setAttribute("userTypevalue",userTypevalue);
		request.setAttribute("status",status);
		request.setAttribute("userintroduction",userintroduction);
		request.setAttribute("password",password);
		request.setAttribute("company",company);
		request.setAttribute("telephone",telephone);
		request.setAttribute("remark",remark);
		request.setAttribute("action", action);
		request.setAttribute("jspTitle", jspTitle);
		request.setAttribute("typeValue1", typeValue1);
		request.setAttribute("typeValue2", typeValue2);
		request.setAttribute("typeValue3", typeValue3);
		request.setAttribute("statusValue0", statusValue0);
		request.setAttribute("statusValue1", statusValue1);
		return "RESULT";
	}
	
	/***************************************************************************
	 * 方法：updateUser
	 * 作者：李雪莹
	 * 日期：2016-08-24
	 * 输入参数：null
	 * 输出参数：RESULT :执行action，跳转至/tomatoAreas/Admin/UserManage/userInfo.jsp
	 * 功能描述：在点击更新按钮，改变用户信息后提交时，通过action匹配到该方法，该方法得到用户所有信息，更新数据库数据
	 * 修改历史：
	 ***************************************************************************/
	//更新用户
	public String updateUser() {
		String userType = session.get("UserType").toString();
		request.setAttribute("userType",userType);
		if(userType.equals("0") ||userType.equals("2")  ){
			request.setAttribute("addBtnType","hidden");
		}else if(userType.equals("1")){
			request.setAttribute("addBtnType","image");
		}
		userId = request.getParameter("UserId");
		userName = request.getParameter("UserName");
		userTypevalue =Integer.parseInt(request.getParameter("UserType"));
		status = Integer.parseInt(request.getParameter("UserStatus"));
		userintroduction = request.getParameter("userintroduction");
		company = request.getParameter("Company");
		password = request.getParameter("password");
		telephone = request.getParameter("Telephone");
		remark = request.getParameter("Remark");
		
		//防止单引号问题
		userintroduction = userintroduction.replaceAll("'", "''");
		remark = remark.replaceAll("'", "''");
		//company = company.replaceAll("'", "''");
				
		if (sqlOperate.login()) {
			usersql = "UPDATE glj_user SET UserName= '"+userName
					+"',UserType = '"+userTypevalue
					+"',Password = '"+password
					+"',UserIntroduction='" + userintroduction
					+ "',CompanyID='" + company 
					+ "',Telephone = '"+telephone
					+"',ExistStatus = '"+status
					+"',Remark = '"+remark
					+"' WHERE UserID='"+ userId + "'";
			int flag = sqlOperate.executeData(usersql);
			if (flag == 0) {
				request.setAttribute("result", "updateFail");
				return "RESULT";
			} else if (flag == 1) {
				request.setAttribute("result", "updateSuccess");
			}
		} else {
			System.err.println("数据库连接失败");
			return "RESULT";
		}
		return "RESULT";
	}
	/**************************************************************************
	 * 方法：showUser
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：null
	 * 输出参数：SUCCESS :执行action，跳转至/pageAreas/Admin/UserManage/showUser.jsp
	 * 功能描述：当点击查看按钮时，获得要查看用户的ID，从数据库中找到该用户取出数据，然后显示出来
	 * 修改历史：
	 * @return
	 **************************************************************************/
	//查看
	public String showUser() {
		userId = request.getParameter("userId");
		//System.out.println(userId);
		if (sqlOperate.login()) {
			usersql = "SELECT * FROM glj_user,glj_company WHERE glj_user.CompanyID = glj_company.CompanyID "
					+"AND glj_user.UserID= '"+ userId + "'";
			ResultSet rs = sqlOperate.queryDataReturnRS(usersql);
			if (rs == null) {
				System.out.println(userId + "读取数据失败");
			} else {
				//System.out.println(userId + "读取数据成功");
				try {
					while(rs!=null && rs.next()){
						request.setAttribute("userName", rs.getString("UserName"));
						if(rs.getInt("UserType") == 1){
							request.setAttribute("userType", "系统管理员");
						}else if(rs.getInt("UserType") == 2){
							request.setAttribute("userType", "公路局");
						}else if(rs.getInt("UserType") == 3){
							request.setAttribute("userType", "管养单位");
						}
						if(rs.getString("UserIntroduction") == null){
							request.setAttribute("userIntroduction", "");
						}else{
							request.setAttribute("userIntroduction", rs.getString("UserIntroduction"));
						}
						request.setAttribute("password", rs.getString("Password"));
						request.setAttribute("company", rs.getString("glj_company.CompanyName"));
						request.setAttribute("telephone", rs.getString("Telephone"));
						request.setAttribute("userId", rs.getString("UserID"));
						status= rs.getInt("ExistStatus");
						String strStatus = "";
						if (status == 0) {
							strStatus = "已删除";
						} else if (status == 1) {
							strStatus = "存在";
						}
						request.setAttribute("status", strStatus);
						if(rs.getString("Remark") == null){
							request.setAttribute("remark","");
						}else
							request.setAttribute("remark", rs.getString("Remark"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.err.println("数据库连接失败");
		}
		return "SUCCESS";
		
		
	}
	/*************************************************************
	 * 方法：checkUser
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：当点击审核按钮，获取要审核的用户ID，然后修改数据库中用户状态，将状态置为审核通过
	 * 修改历史：
	 *************************************************************/
	//审核用户
	public String checkUser(){
		approverName = session.get("UserName").toString();
		userId = request.getParameter("userId");
		status = Integer.parseInt(request.getParameter("status"));
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		approveTime = sdf.format(d);
		usersql = "UPDATE tws_userinfo SET Status='" + status+ "',ApproverName='" + approverName 
				+ "',ApproveTime='" + approveTime +  "' WHERE UserID='"
				+ userId + "'";
		if (sqlOperate.login()) {
			int flag = sqlOperate.executeData(usersql);
			if (flag == 0) {
				System.out.println("审核失败");
			} else if (flag == 1) {
				System.out.println("审核成功");
			}
		} else {
			System.err.println("数据库连接失败");
		}
		return null;
	}
	
	/*************************************************************
	 * 方法：deleteUser
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：当点击删除按钮时，获取要删除的用户ID，然后将用户状态置为已删除，但是是逻辑的删除
	 * 修改历史：
	 *************************************************************/
	//逻辑删除用户
	public String deleteUser() throws UnsupportedEncodingException{
		userName = request.getParameter("userName");
		try {
			userName= URLDecoder.decode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //System.out.println(userName);
	    status = 0;
		usersql = "UPDATE glj_user SET ExistStatus='" + status+  "' WHERE UserName='"
				+ userName + "'";
	    if(sqlOperate.login())
	    {
	    	int flag = sqlOperate.executeData(usersql);
	    	if(flag == 1){
	    			System.out.println("删除成功！");
	    	}else if(flag == 0){
	    			System.out.println("删除失败！");
	    	}
	    }else {
			System.err.println("数据库连接失败");
		}
		return null;
	}
	
}
