package com.zsglj.admin.actions.company;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zsglj.dao.SqlOperate;

public class CompanyOperation extends ActionSupport{

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map session = ActionContext.getContext().getSession();
	
	private String usersql;
	private SqlOperate sqlOperate;
	private String companyId;
	private String companyName;
	private int companyTypevalue;
	private String companyintroduction;
	private String companyLoc;
	private int status;
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
	
	public CompanyOperation(){
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
		companyName = request.getParameter("CompanyName");
		try {
			companyName= URLDecoder.decode(companyName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("添加的单位名为："+companyName);
		if (sqlOperate.login()) {
			usersql = "select CompanyName from glj_company where CompanyName = '"+companyName+"'";
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
	public String addCompany() {
		companyId = request.getParameter("CompanyID");
		String userType = session.get("UserType").toString();
		request.setAttribute("userType",userType);
		if(userType.equals("3") ||userType.equals("2")  ){
			request.setAttribute("addBtnType","hidden");
		}else if(userType.equals("1")){
			request.setAttribute("addBtnType","image");
		}
		companyName = request.getParameter("CompanyName");
		companyTypevalue =Integer.parseInt(request.getParameter("ComType"));
		companyintroduction = request.getParameter("companyintroduction");
		companyLoc = request.getParameter("CompanyLoc");
		
		//防止单引号问题
		companyintroduction = companyintroduction.replaceAll("'", "''");
		companyLoc = companyLoc.replaceAll("'", "''");
		if (sqlOperate.login()) {
			usersql = "INSERT INTO glj_company(CompanyID,CompanyName,ComIntroduction,ComLocation,ComType,ExistStatus) VALUES('"
					+companyId+"','"+ companyName+ "','"+ companyintroduction+ "','"
					+companyLoc+"','"+companyTypevalue+"','"+1+"')";
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
	public String putupdateCom(){
		companyId = request.getParameter("companyId");
	    status = -1;
		companyName = "";
		companyTypevalue=0;
		companyintroduction = "";
		companyLoc = "";
		String  typeValue1 = "", typeValue2 = "";
		String  statusValue0 = "", statusValue1 = "";
		String action = "actionsAdminCompany/addCompany";
		String jspTitle = "添加单位";
		if (companyId != null) {
			jspTitle = "更新单位";
			action = "actionsAdminCompany/updateCom";
			sqlOperate.login();
			usersql = "select * from glj_company WHERE CompanyID= '"+companyId+"'";
			try {
				ResultSet rs = sqlOperate.queryDataReturnRS(usersql);
				while (rs!=null && rs.next()) {
					companyName = rs.getString("CompanyName");
					companyTypevalue = rs.getInt("ComType");
					status = rs.getInt("ExistStatus");
					companyintroduction = rs.getString("ComIntroduction");
					companyLoc = rs.getString("ComLocation");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (companyTypevalue == 1) {
				typeValue1 = "selected=\"selected\"";
			} else if (companyTypevalue == 2) {
				typeValue2 = "selected=\"selected\"";
			}
			if (status == 0) {
				statusValue0 = "selected=\"selected\"";
			} else if (status == 1) {
				statusValue1 = "selected=\"selected\"";
			}
		}else{
			companyId = UUID.randomUUID().toString();
		}
		request.setAttribute("companyId", companyId);
		request.setAttribute("companyName",companyName);
		request.setAttribute("companyTypevalue",companyTypevalue);
		request.setAttribute("status",status);
		request.setAttribute("companyintroduction",companyintroduction);
		request.setAttribute("companyLoc",companyLoc);
		request.setAttribute("action", action);
		request.setAttribute("jspTitle", jspTitle);
		request.setAttribute("typeValue1", typeValue1);
		request.setAttribute("typeValue2", typeValue2);
		request.setAttribute("statusValue0", statusValue0);
		request.setAttribute("statusValue1", statusValue1);
		return "RESULT";
	}
	
	/***************************************************************************
	 * 方法：updateUser
	 * 作者：李雪莹
	 * 日期：2016-08-24
	 * 输入参数：null
	 * 输出参数：RESULT :执行action，跳转至/pageAreas/Admin/CompanyManage/companyInfo.jsp
	 * 功能描述：在点击更新按钮，改变用户信息后提交时，通过action匹配到该方法，该方法得到用户所有信息，更新数据库数据
	 * 修改历史：
	 ***************************************************************************/
	//更新单位
	public String updateCom() {
		String userType = session.get("UserType").toString();
		request.setAttribute("userType",userType);
		if(userType.equals("0") ||userType.equals("2")  ){
			request.setAttribute("addBtnType","hidden");
		}else if(userType.equals("1")){
			request.setAttribute("addBtnType","image");
		}
		companyId = request.getParameter("CompanyID");
		companyName = request.getParameter("CompanyName");
		companyTypevalue =Integer.parseInt(request.getParameter("ComType"));
		status = Integer.parseInt(request.getParameter("ComStatus"));	
		companyintroduction = request.getParameter("companyintroduction");
		companyLoc = request.getParameter("CompanyLoc");
		
		//防止单引号问题
		companyintroduction = companyintroduction.replaceAll("'", "''");
		companyLoc = companyLoc.replaceAll("'", "''");

		if (sqlOperate.login()) {
			usersql = "UPDATE glj_company SET CompanyName= '"+companyName
					+"',ComType = '"+companyTypevalue
					+"',ComIntroduction='" + companyintroduction
					+ "',ComLocation='" + companyLoc 
					+"',ExistStatus = '"+ status
					+"' WHERE CompanyID='"+ companyId + "'";
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
	 * 方法：showCompany
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：null
	 * 输出参数：SUCCESS :执行action，跳转至/pageAreas/Admin/UserManage/showUser.jsp
	 * 功能描述：当点击查看按钮时，获得要查看用户的ID，从数据库中找到该用户取出数据，然后显示出来
	 * 修改历史：
	 * @return
	 **************************************************************************/
	//查看
	public String showCompany() {
		companyId = request.getParameter("companyId");
		if (sqlOperate.login()) {
			usersql = "SELECT * FROM glj_company WHERE CompanyID= '"
					+ companyId + "'";
			ResultSet rs = sqlOperate.queryDataReturnRS(usersql);
			if (rs == null) {
				System.out.println(companyId + "读取数据失败");
			} else {
				System.out.println(companyId + "读取数据成功");
				try {
					while(rs!=null && rs.next()){
						request.setAttribute("companyId", companyId);
						request.setAttribute("companyName", rs.getString("CompanyName"));
						if(rs.getInt("ComType") == 1){
							request.setAttribute("comType", "管养单位");
						}else if(rs.getInt("ComType") == 2){
							request.setAttribute("comType", "非管养单位");
						}
						if(rs.getString("ComIntroduction") == null){
							request.setAttribute("comIntroduction", "");
						}else{
							request.setAttribute("comIntroduction", rs.getString("ComIntroduction"));
						}
						request.setAttribute("companyLoc", rs.getString("ComLocation"));
						status= rs.getInt("ExistStatus");
						String strStatus = "";
						if (status == 0) {
							strStatus = "已删除";
						} else if (status == 1) {
							strStatus = "存在";
						}
						request.setAttribute("status", strStatus);
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
	 * 方法：deleteCompany
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：当点击删除按钮时，获取要删除的用户ID，然后将用户状态置为已删除，但是是逻辑的删除
	 * 修改历史：
	 *************************************************************/
	//逻辑删除用户
	public String deleteCompany() throws UnsupportedEncodingException{
		companyName = request.getParameter("companyName");
		try {
			companyName= URLDecoder.decode(companyName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //System.out.println(userName);
	    status = 0;
		usersql = "UPDATE glj_company SET ExistStatus='" + status+  "' WHERE CompanyName='"
				+ companyName + "'";
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
