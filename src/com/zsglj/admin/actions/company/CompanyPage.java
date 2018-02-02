package com.zsglj.admin.actions.company;

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
import com.zsglj.bean.Company;
import com.zsglj.util.PageUtil;

public class CompanyPage extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response=ServletActionContext.getResponse();
	private Map session = ActionContext.getContext().getSession();
	private int rows;
	private int page;
	//三个查询条件变量
	public String companyTypevalue;
	public String queryStatus;
	public String queryCompanyName;
	public static String companyQuerySQL=null;
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
	public String companyInfo(){
		companyQuerySQL = null;
		String userType = session.get("UserType").toString();
		request.setAttribute("userType",userType);
		if(userType.equals("0") ||userType.equals("2")  ){
			request.setAttribute("addBtnType","hidden");
		}else if(userType.equals("1")){
			request.setAttribute("addBtnType","image");
		}
		return SUCCESS;
	}
	public String updateCompany(){
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
	public String companylistData(){
		ActionContext ctx=ActionContext.getContext();	
		List<Company> list = new ArrayList<Company>();
		String strsql;
		String result = "";
		if(companyQuerySQL==null){
			strsql = " glj_company ";
		}else{
			strsql = " glj_company "+companyQuerySQL+"   ";
		}
		ResultSet rs = PageUtil.getPage(page,rows,strsql);
		try{
			while(rs!=null && rs.next()){
				Company company = new Company();
				company.setCompanyID(rs.getString("CompanyID"));
				//company.setUserID(rs.getString("UserID"));
				company.setCompanyName(rs.getString("CompanyName"));
				company.setComIntroduction(rs.getString("ComIntroduction"));
				company.setComLocation(rs.getString("ComLocation"));
				company.setComType(rs.getInt("ComType"));
				company.setStatus(rs.getInt("ExistStatus"));
				list.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	//接下来的是把整个对象转json格式   对象有两个属性 total和rows属性  而rows里面又是一个数组 
		JSONObject jo = new JSONObject();
		jo.put("total", PageUtil.getDataCounts(strsql));
		jo.put("rows", list);
		result = jo.toString();
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
	public String querycompanylist() throws UnsupportedEncodingException{
		queryStatus =  request.getParameter("queryStatus");
		companyTypevalue= request.getParameter("companyTypevalue");
		queryCompanyName= request.getParameter("queryCompanyName");
		String sql1 = " WHERE CompanyID != 'null' ";
		if(!queryStatus.equals("3")){
			sql1=sql1+" AND ExistStatus='"+queryStatus+"' ";
		}
		if(!companyTypevalue.equals("0")){
			sql1 =sql1+" AND ComType='"+companyTypevalue+"' ";
		}
		if(!queryCompanyName.isEmpty()){
			sql1 =sql1+" AND CompanyName LIKE '%"+queryCompanyName+"%' ";
		}
		companyQuerySQL=sql1;
		return null;
	}
	
}
