package com.zsglj.company.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zsglj.bean.Event;
import com.zsglj.util.PageUtil;

public class EventPage extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response=ServletActionContext.getResponse();
	private Map session = ActionContext.getContext().getSession();
	private int rows;
	private int page;
	private String queryTypeName;      //事件类型
	private String queryUploadName;		//上报人
	private String queryStatus;		//事件状态
	private String queryUploadTimeFrom;		//从日期
	private String queryUploadTimeTo;			//到日期
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
	
	public String eventInfo(){
		userQuerySQL = null;
		String userType = session.get("UserType").toString();
		request.setAttribute("UserType",userType);
		return "success";
	}
	
	/*****************************************************************************************
	 * 方法：eventlistData
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：在用户管理界面，将数据库中的待审核及审核通过事件信息显示出来。如果没有查询条件，将显示所有，如果有查询条件，将按查询条件显示。
	 * 修改历史：
	 *****************************************************************************************/
	@SuppressWarnings("rawtypes")
	public String eventlistData(){
		// ActionContext ctx=ActionContext.getContext();	
		Map session1 = ActionContext.getContext().getSession();
		String type = String.valueOf(session1.get("UserType"));
		// System.out.println(session1.get("UserType"));
		List<Event> list = new ArrayList<Event>();
		String result = "";
		String strsql;
		if(userQuerySQL==null){
			strsql = " glj_event,glj_company WHERE glj_event.CompanyID=glj_company.CompanyID AND glj_event.ExistStatus != '0'";
		}else {
			strsql = " glj_event,glj_company WHERE glj_event.CompanyID=glj_company.CompanyID "+userQuerySQL;
		}
		if ("2".equals(type)) {           //如果为公路局
			strsql += " AND Status in (2,3) ";
		}
		strsql += " ORDER BY EventDate DESC ";
		// System.out.println("page:"+page+"  rows:"+rows+" sql:"+strsql);
		ResultSet rs = PageUtil.getPage(page,rows,strsql);
		try{
			while(rs!=null && rs.next()){
				Event e = new Event();
				e.setEventID(rs.getString("EventID")+"");
				e.setCompanyID(rs.getString("CompanyName"));
				e.setEventDate(rs.getString("EventDate"));
				e.setEventTime(rs.getString("EventTime"));
				e.setTypeID(rs.getString("TypeID"));
				e.setRoadLocation(rs.getString("RoadLocation"));
				e.setEventCase(rs.getString("EventCase"));
				e.setTrafficCase(rs.getString("TrafficCase"));
				e.setTownID(rs.getString("TownID"));
				e.setStatus(rs.getInt("Status"));
				// e.setPicture(rs.getString("Picture"));
				
				list.add(e);
				// System.out.println(e.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("事件数量:"+list.size());
		//接下来的是吧整个对象转json格式  对象有两个属性 total和rows属性  而rows里面又是一个数组 
		JSONObject jo = new JSONObject();
		jo.put("total", PageUtil.getDataCounts(strsql));        		// PageUtil.getDataCounts(strsql)
		jo.put("rows", list);
		result = jo.toString();
		// System.out.println(result);
		try {
			response.setContentLength(result.getBytes("UTF-8").length);
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(result);
			response.getWriter().flush();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/******************************************************************************
	 * 方法：queryeventlist
	 * 作者：李雪莹
	 * 日期：2016-08-25
	 * 输入参数：
	 * 输出参数：
	 * 功能描述：在用户管理界面，可以根据需要查找相应的用户，如：日期限制，上传者限制，查找用户身份限制等。该方法获得查询条件。
	 * 修改历史：
	 ******************************************************************************/
	public String queryeventlist() throws Exception{
		queryStatus =  request.getParameter("queryStatus");       		//状态和类型名称为 数字
		queryTypeName= request.getParameter("queryTypeName");
		queryUploadName= request.getParameter("queryUploadName");
		queryUploadTimeFrom=  request.getParameter("queryUploadTimeFrom");
		queryUploadTimeTo=  request.getParameter("queryUploadTimeTo");
		// System.out.println(queryUploadTimeFrom+"--"+queryUploadTimeTo); //2018-01-04--2018-02-01
		try {
			queryUploadName= URLDecoder.decode(queryUploadName, "UTF-8");
			queryTypeName= URLDecoder.decode(queryTypeName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(queryStatus+"-"+queryTypeName+"-"+queryUploadName+"-"+queryUploadTimeFrom+"-"+queryUploadTimeTo);
		String sql1 = " ";
		if(!queryStatus.equals("0")){
			sql1=sql1+" AND STATUS='"+queryStatus+"' ";
		}
		if(!queryTypeName.isEmpty()&&!"0".equals(queryTypeName)){
			sql1=sql1+" AND TypeID='"+queryTypeName+"' ";
		}
		if(!queryUploadTimeFrom.isEmpty() && !queryUploadTimeTo.isEmpty()){
			sql1 = sql1+" AND EventDate BETWEEN '"+queryUploadTimeFrom+"' AND '"+queryUploadTimeTo+"' ";
		}
		if(!queryUploadName.isEmpty()){    // 上报人
			sql1 =sql1+" AND UserID in (select UserID from glj_user where UserName like '%"+queryUploadName+"%')";
		}
		userQuerySQL=sql1;
		return null;
	}
}
