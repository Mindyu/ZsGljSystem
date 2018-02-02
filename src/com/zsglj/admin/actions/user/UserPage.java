package com.zsglj.admin.actions.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zsglj.util.PageUtil;
import com.zsglj.bean.User;

public class UserPage extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response=ServletActionContext.getResponse();
	private Map session = ActionContext.getContext().getSession();
	private int rows;
	private int page;
	//三查询条件变量
	public String userTypevalue;
	public String queryStatus;
	public String queryUserName;
	public static String userQuerySQL=null;
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String userInfo(){
		userQuerySQL = null;
		request.setAttribute("addBtnType","image");
		return SUCCESS;
	}
	public String updateUser(){
		return SUCCESS;
	}
	
	/*****************************************************************************************
	 * 方法：userlistData
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：在用户管理界面，将数据库中的待审核及审核通过用户信息显示出来。如果没有查询条件，将显示所有，如果有查询条件，将按查询条件显示。
	 * 修改历史：
	 *****************************************************************************************/
	public String userlistData(){
		ActionContext ctx=ActionContext.getContext();	
		request.setAttribute("addBtnType","image");
		List<User> list = new ArrayList<User>();
		String strsql;
		String result = "";
		if(userQuerySQL==null){
			strsql = " glj_user,glj_company WHERE glj_user.CompanyID = glj_company.CompanyID ";
		}else{
			strsql = " glj_user,glj_company WHERE glj_user.CompanyID = glj_company.CompanyID  "+userQuerySQL+"   ";
		}
		ResultSet rs = PageUtil.getPage(page,rows,strsql);
		try{
			while(rs!=null && rs.next()){
				User u = new User();
				u.setUserID(rs.getString("UserID")+"");
				u.setUserName(rs.getString("UserName"));
				u.setUserType(rs.getInt("UserType"));
				u.setCompanyID(rs.getString("CompanyName"));	
				u.setUserIntroduction("UserIntroduction");
				u.setTelephone(rs.getString("Telephone"));
				u.setStatus(rs.getInt("ExistStatus"));
				u.setRemark(rs.getString("Remark"));
				list.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	//接下来的是把整个对象转json格式   对象有两个属性 total和rows属性  而rows里面又是一个数组 
		JSONObject jo = new JSONObject();
		jo.put("total", PageUtil.getDataCounts(strsql));
		jo.put("rows", list);
		result = jo.toString();
		//System.out.println(result);
		try {
			response.setContentLength(result.getBytes("UTF-8").length);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(result);	
			response.getWriter().flush();
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/******************************************************************************
	 * 方法：queryuserlist
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：
	 * 输出参数：
	 * 功能描述：在用户管理界面，可以根据需要查找相应的用户，如：日期限制，上传者限制，查找用户身份限制等。该方法获得查询条件。
	 * 修改历史：
	 ******************************************************************************/
	public String queryuserlist() throws UnsupportedEncodingException{
		queryStatus =  request.getParameter("queryStatus");
		userTypevalue= request.getParameter("userTypevalue");
		queryUserName= request.getParameter("queryUserName");
		String sql1 = "  ";
		if(!queryStatus.equals("3")){
			sql1=sql1+" AND glj_user.ExistStatus='"+queryStatus+"' ";
		}
		if(!userTypevalue.equals("0")){
			sql1 =sql1+" AND glj_user.UserType='"+userTypevalue+"' ";
		}
		if(!queryUserName.isEmpty()){
			sql1 =sql1+" AND glj_user.UserName LIKE '%"+queryUserName+"%' ";
		}
		userQuerySQL=sql1;
		return null;
	}
	
}
