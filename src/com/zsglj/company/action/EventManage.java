package com.zsglj.company.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zsglj.bean.Event;
import com.zsglj.dao.EventDao;
import com.zsglj.dao.UserDao;
import com.zsglj.util.ConnectSQL;
import com.zsglj.util.GetUUID;

public class EventManage extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map session = ActionContext.getContext().getSession();
	private Event event = new Event();
	private InputStream revokeReasonStream;
	
	public InputStream getRevokeReasonStream() {
		return revokeReasonStream;
	}
	
	public void setRevokeReasonStrem(InputStream stream) {
		this.revokeReasonStream = stream;
	}

	public EventManage() {
		request = ServletActionContext.getRequest();
	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String showEvent() throws Exception {
		String eventId = request.getParameter("eventID");
		// System.out.println("事件ID" + eventId );
		String sql = "SELECT * FROM glj_event WHERE EventID= '" + eventId + "'";
		PreparedStatement pstate = ConnectSQL.getConnection().prepareStatement(sql);
		ResultSet rs = pstate.executeQuery();
		if (rs != null) {
			while (rs.next()) {
				request.setAttribute("eventDate", rs.getString("EventDate"));
				request.setAttribute("eventTime", rs.getString("EventTime"));
				request.setAttribute("typeID", rs.getString("TypeID"));
				request.setAttribute("picture", rs.getString("Picture"));
				request.setAttribute("roadLocation", rs.getString("RoadLocation"));
				request.setAttribute("eventCase", rs.getString("EventCase"));
				request.setAttribute("trafficCase", rs.getString("TrafficCase"));
				request.setAttribute("townID", rs.getString("TownID"));
				request.setAttribute("finishTime", rs.getString("FinishTime"));
				request.setAttribute("finishcase", rs.getString("Finishcase"));
				request.setAttribute("peopleNum", rs.getInt("peopleNum"));
				request.setAttribute("carNum", rs.getString("CarNum"));
				request.setAttribute("reportedTime", rs.getString("ReportedTime"));
				request.setAttribute("userID", rs.getString("UserID"));
				request.setAttribute("dutyNum", rs.getInt("DutyNum"));
				request.setAttribute("remark", rs.getString("Remark"));
				request.setAttribute("status", rs.getString("Status"));
				request.setAttribute("returnReason", rs.getString("ReturnReason"));
				request.setAttribute("revokeReason", rs.getString("RevokeReason"));
			}
		} else {
			System.out.println("数据库数据有误！");
			return null;
		}
		return SUCCESS;
	}
	
	/*************************************************************
	 * 方法：submitEvent
	 * 作者：杨陈强
	 * 日期：2018-01-30
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：当点击提交按钮，获取要提交事件的D，然后修改数据库中事件状态，将状态置为2,更新上报人和上报时间
	 * 修改历史：
	 *************************************************************/
	public String submitEvent() throws Exception {
		String eventId = request.getParameter("eventID");
		// System.out.println("事件ID:" + eventId );
		String userID = session.get("UserID").toString();
		// System.out.println("UserID:"+userID);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reportTime= sdf.format(d);
		String sql = "UPDATE glj_event SET Status='2',ReportedTime = '"+ reportTime +"',UserID = '"+ userID +"' WHERE EventID= '" + eventId + "'";
		System.out.println(sql);
		PreparedStatement pstate = ConnectSQL.getConnection().prepareStatement(sql);
		int rs = pstate.executeUpdate();
		if (rs>0) {
			System.out.println("提交成功！");
		}else {
			System.out.println("提交失败！");
		}
		return null;
	}

	/*************************************************************
	 * 方法：deleteEvent
	 * 作者：杨陈强
	 * 日期：2018-01-30
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：当点击提交按钮，获取要提交事件的D，然后修改数据库中事件状态，将存在状态置为0
	 * 修改历史：
	 *************************************************************/
	public String deleteEvent() throws Exception {
		String eventId = request.getParameter("eventID");
		// System.out.println("事件ID" + eventId );
		String sql = "UPDATE glj_event SET ExistStatus='0' WHERE EventID= '" + eventId + "'";
		PreparedStatement pstate = ConnectSQL.getConnection().prepareStatement(sql);
		int rs = pstate.executeUpdate();
		if (rs>0) {
			System.out.println("删除成功！");
		}else {
			System.out.println("删除失败！");
		}
		return null;
	}
	
	/*************************************************************
	 * 方法：agreeReturnEvent
	 * 作者：杨陈强
	 * 日期：2018-01-30
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：当点击同意撤回按钮，获取要提交事件的D，然后修改数据库中事件状态，将状态置为4
	 * 修改历史：
	 *************************************************************/
	public String agreeReturnEvent() throws Exception {
		String eventId = request.getParameter("eventID");
		String sql = "UPDATE glj_event SET Status='4' WHERE EventID= '" + eventId + "'";
		PreparedStatement pstate = ConnectSQL.getConnection().prepareStatement(sql);
		int rs = pstate.executeUpdate();
		if (rs>0) {
			System.out.println("同意撤回成功！");
		}else {
			System.out.println("同意撤回失败！");
		}
		return null;
	}
	
	
	/*************************************************************
	 * 方法：addEvent
	 * 作者：杨陈强
	 * 日期：2018-01-31
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：当点击提交事件按钮，向数据库中添加一条事件信息，成功之后返回事件列表页面
	 * 修改历史：
	 *************************************************************/
	public String addEvent() throws Exception {
		String eventId = request.getParameter("eventID");           // 自动生成的信息
		String userId = session.get("UserID").toString();
		String companyId = UserDao.queryCompanyIdByUserId(userId);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reportTime= sdf.format(d);
		event.setEventID(eventId);
		event.setUserID(userId);
		event.setCompanyID(companyId);
		event.setReportedTime(reportTime);
		event.setStatus(1); 
		event.setEventDate(request.getParameter("EventDate"));		// 以下为所填表单信息
		event.setEventTime(request.getParameter("EventTime"));
		event.setTypeID(request.getParameter("TypeID"));
		event.setRoadLocation(request.getParameter("RoadLocation"));
		event.setEventCase(request.getParameter("EventCase"));
		event.setTrafficCase(request.getParameter("TrafficCase"));
		event.setTownID(request.getParameter("TownID"));
		event.setPicture("");				// request.getParameter("Picture")
		event.setFinishTime(request.getParameter("FinishTime"));
		event.setFinishCase(request.getParameter("FinishCase"));
		event.setFinishPic("");				//	request.getParameter("FinishPic")
		event.setPeopleNum(Integer.valueOf(request.getParameter("PeopleNum")));
		event.setCarNum(Integer.valueOf(request.getParameter("CarNum")));
		event.setDutyNum(Integer.valueOf(request.getParameter("DutyNum")));
		event.setBadRoadNum(Integer.valueOf(request.getParameter("BadRoadNum")));
		event.setUnattendedNum(Integer.valueOf(request.getParameter("UnattendedNum")));
		event.setSundriesNum(Integer.valueOf(request.getParameter("SundriesNum")));
		event.setDenoterNum(Integer.valueOf(request.getParameter("DenoterNum")));
		event.setOther(request.getParameter("Other"));
		event.setRemark(request.getParameter("Remark"));
		
		// System.out.println(event.toString());
		EventDao.saveEvent(event);
		return SUCCESS;
	}

	/*************************************************************
	 * 方法：updateEvent
	 * 作者：杨陈强
	 * 日期：2018-01-31
	 * 输入参数：null
	 * 输出参数：null
	 * 功能描述：当点击提交事件按钮，向数据库中修改对应事件信息，成功之后返回事件列表页面
	 * 修改历史：
	 *************************************************************/
	public String updateEvent() throws Exception {
		String eventId = request.getParameter("eventID");
		String userId = session.get("UserID").toString();
		String companyId = UserDao.queryCompanyIdByUserId(userId);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reportTime= sdf.format(d);
		event.setEventID(eventId);
		event.setUserID(userId);
		event.setCompanyID(companyId);
		event.setReportedTime(reportTime);
		event.setStatus(1); 					//更新后状态为  已保存
		event.setEventDate(request.getParameter("EventDate"));
		event.setEventTime(request.getParameter("EventTime"));
		event.setTypeID(request.getParameter("TypeID"));
		event.setRoadLocation(request.getParameter("RoadLocation"));
		event.setEventCase(request.getParameter("EventCase"));
		event.setTrafficCase(request.getParameter("TrafficCase"));
		event.setTownID(request.getParameter("TownID"));
		event.setPicture("");				// request.getParameter("Picture")
		event.setFinishTime(request.getParameter("FinishTime"));
		event.setFinishCase(request.getParameter("FinishCase"));
		event.setFinishPic("");				//	request.getParameter("FinishPic")
		event.setPeopleNum(Integer.valueOf(request.getParameter("PeopleNum")));
		event.setCarNum(Integer.valueOf(request.getParameter("CarNum")));
		event.setDutyNum(Integer.valueOf(request.getParameter("DutyNum")));
		event.setBadRoadNum(Integer.valueOf(request.getParameter("BadRoadNum")));
		event.setUnattendedNum(Integer.valueOf(request.getParameter("UnattendedNum")));
		event.setSundriesNum(Integer.valueOf(request.getParameter("SundriesNum")));
		event.setDenoterNum(Integer.valueOf(request.getParameter("DenoterNum")));
		event.setOther(request.getParameter("Other"));
		event.setRemark(request.getParameter("Remark"));
		
		// System.out.println(event.toString());
		EventDao.updateEvent(event);
		return SUCCESS;
	}
	
	public String returnEvent() throws Exception {
		String eventId = request.getParameter("eventID");
		String returnReason = request.getParameter("returnReason");
		String sql = "UPDATE glj_event SET Status='5', returnReason='" + returnReason + "' WHERE EventID= '" + eventId + "'";
		PreparedStatement pstate = ConnectSQL.getConnection().prepareStatement(sql);
		int rs = pstate.executeUpdate();
		if (rs > 0) {
			System.out.println("原因添加成功！");
		} else {
			System.out.println("原因添加失败！");
		}
		return null;
	}
	
	public String revokeEvent() throws Exception {
		String eventId = request.getParameter("eventID");
		String revokeReason = request.getParameter("revokeReason");
		String sql = "UPDATE glj_event SET Status='3', revokeReason='" + revokeReason + "' WHERE EventID= '" + eventId + "'";
		PreparedStatement pstate = ConnectSQL.getConnection().prepareStatement(sql);
		int rs = pstate.executeUpdate();
		if (rs > 0) {
			System.out.println("撤回原因添加成功！");
		} else {
			System.out.println("撤回原因添加失败！");
		}
		return null;
	}
	
	public String viewRevokeReason() throws Exception {
		String eventId = request.getParameter("eventID");
		String sql = "SELECT RevokeReason FROM glj_event WHERE EventID= '" + eventId + "'";
		PreparedStatement pstate = ConnectSQL.getConnection().prepareStatement(sql);
		ResultSet rs = pstate.executeQuery();
		if (rs != null) {
			while (rs.next()) {
				revokeReasonStream = new ByteArrayInputStream(
						rs.getString("RevokeReason").getBytes(StandardCharsets.UTF_8));
				System.out.println("撤回原因查询成功");
			}
			return SUCCESS;
		} else {
			System.out.println("数据库数据有误！");
			return null;
		}
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
	public String putupdateEvent(){
		event.setEventID(request.getParameter("eventID"));
		event.setEventDate("");
		event.setEventTime("");
		event.setTypeID("");
		event.setRoadLocation("");
		event.setEventCase("");
		event.setTrafficCase("");
		event.setTownID("");
		event.setPicture("");
		event.setFinishTime("");
		event.setFinishCase("");
		event.setOther("");
		event.setRemark("");
		event.setPicture("/ZsGljSystem/icons/Event/nopic.jpg");
		event.setFinishPic("/ZsGljSystem/icons/Event/nopic.jpg");
		String action = "actionEvent/addEvent";
		String jspTitle = "添加事件";
		System.out.println("事件ID："+event.getEventID());
		if (event.getEventID() != null && !"".equals(event.getEventID())) {
			System.out.println("修改事件");
			jspTitle = "修改事件";
			action = "actionEvent/updateEvent";
			String sql = "select * from glj_event WHERE EventID= '"+event.getEventID()+"'";
			try {
				PreparedStatement pstmt = ConnectSQL.getConnection().prepareStatement(sql); 
				ResultSet rs = pstmt.executeQuery();
				while (rs!=null && rs.next()) {
					event.setEventDate(rs.getString("EventDate"));
					event.setEventTime(rs.getString("EventTime"));
					event.setTypeID(rs.getString("TypeID"));
					event.setRoadLocation(rs.getString("RoadLocation"));
					event.setEventCase(rs.getString("EventCase"));
					event.setTrafficCase(rs.getString("TrafficCase"));
					event.setTownID(rs.getString("TownID"));
					event.setPicture(rs.getString("Picture"));
					event.setFinishTime(rs.getString("FinishTime"));
					event.setFinishCase(rs.getString("FinishCase"));
					event.setPeopleNum(rs.getInt("PeopleNum"));
					event.setCarNum(rs.getInt("CarNum"));
					event.setFinishPic(rs.getString("FinishPic"));
					event.setDutyNum(rs.getInt("DutyNum"));
					event.setBadRoadNum(rs.getInt("BadRoadNum"));
					event.setUnattendedNum(rs.getInt("UnattendedNum"));
					event.setSundriesNum(rs.getInt("SundriesNum"));
					event.setDenoterNum(rs.getInt("DenoterNum"));
					event.setOther(rs.getString("Other"));
					event.setRemark(rs.getString("Remark"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("新增事件");
			event.setEventID(GetUUID.getID()); 
			/*Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			createTime= sdf.format(d);
			request.setAttribute("createTime", createTime);*/
		}
		// System.out.println("userId="+userId);
		request.setAttribute("eventID", event.getEventID());
		request.setAttribute("EventDate", event.getEventDate());
		request.setAttribute("EventTime", event.getEventTime());
		request.setAttribute("TypeID",event.getTypeID());
		request.setAttribute("RoadLocation",event.getRoadLocation());
		request.setAttribute("EventCase",event.getEventCase());
		request.setAttribute("TrafficCase",event.getTrafficCase());
		request.setAttribute("TownID",event.getTownID());
		request.setAttribute("Picture",event.getPicture());
		request.setAttribute("FinishTime",event.getFinishTime());
		request.setAttribute("FinishCase",event.getFinishCase());
		request.setAttribute("PeopleNum",event.getPeopleNum());
		request.setAttribute("CarNum",event.getCarNum());
		request.setAttribute("FinishPic",event.getFinishPic());
		request.setAttribute("DutyNum", event.getDutyNum());
		request.setAttribute("BadRoadNum", event.getBadRoadNum());
		request.setAttribute("UnattendedNum", event.getUnattendedNum());
		request.setAttribute("SundriesNum", event.getSundriesNum());
		request.setAttribute("DenoterNum", event.getDenoterNum());
		request.setAttribute("Other", event.getOther());
		request.setAttribute("Remark", event.getRemark());
		request.setAttribute("action", action+"?eventID="+event.getEventID());
		request.setAttribute("jspTitle", jspTitle);
		return "RESULT";
	}
}