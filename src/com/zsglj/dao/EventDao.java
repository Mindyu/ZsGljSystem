package com.zsglj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zsglj.bean.Event;
import com.zsglj.util.ConnectSQL;

public class EventDao {

	public static void updateEvent(Event e){
		Connection conn = ConnectSQL.getConnection();
		String sql = "UPDATE glj_event SET CompanyID ='"+e.getCompanyID()+
				"', EventDate ='"+e.getEventDate()+"', EventTime ='"+e.getEventTime()+
				"', TypeID ='"+e.getTypeID()+"', RoadLocation ='"+e.getRoadLocation()+
				"', EventCase ='"+e.getEventCase()+"', TrafficCase ='"+e.getTrafficCase()+
				"', TownID ='"+e.getTownID()+"', Picture='"+e.getPicture()+"', FinishTime ='"+e.getFinishTime()+
				"', FinishCase ='"+e.getFinishCase()+"', PeopleNum ='"+e.getPeopleNum()+
				"', CarNum ='"+e.getCarNum()+"', FinishPic ='"+e.getFinishPic()+
				"', ReportedTime ='"+e.getReportedTime()+"', UserID ='"+e.getUserID()+
				"', DutyNum ='"+e.getDutyNum()+"', BadRoadNum ='"+e.getBadRoadNum()+
				"', UnattendedNum ='"+e.getUnattendedNum()+"', SundriesNum ='"+e.getSundriesNum()+
				"', DenoterNum ='"+e.getDenoterNum()+"', Status ='"+e.getStatus()+
				"', Other ='"+e.getOther()+"', ReturnReason ='"+e.getReturnReason()+
				"', RevokeReason ='"+e.getRevokeReason()+"', ExistStatus = '1' , Remark ='"+e.getRemark()+
				"'  WHERE EventID ='"+e.getEventID()+"' ";
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			// System.out.println(sql);
			int rs = pstate.executeUpdate();
			if (rs==1) {
				System.out.println("事件修改成功");
			}
		} catch (SQLException e1) {
			System.out.println("事件修改失败");
			e1.printStackTrace();
		}
	}
	
	public static void saveEvent(Event e){
		Connection conn = ConnectSQL.getConnection();
		String sql = "INSERT INTO glj_event ("
				+ "EventID, CompanyID, EventDate, EventTime, TypeID, RoadLocation, "
				+ "EventCase, TrafficCase, TownID, Picture, FinishTime, FinishCase, "
				+ "PeopleNum, CarNum, FinishPic, ReportedTime, UserID, DutyNum, "
				+ "BadRoadNum, UnattendedNum, SundriesNum, DenoterNum, Status, "
				+ "Other, ExistStatus, Remark) VALUES ("
				+ "'"+e.getEventID()+"', '"+e.getCompanyID()+"', '"+e.getEventDate()+"', '"+e.getEventTime()
				+"', '"+e.getTypeID()+"', '"+e.getRoadLocation()+"', '"+e.getEventCase()+"', '"+e.getTrafficCase()
				+"', '"+e.getTownID()+"', '"+e.getPicture()+"', '"+e.getFinishTime()+"', '"+e.getFinishCase()
				+"', '"+e.getPeopleNum()+"', '"+e.getCarNum()+"', '"+e.getFinishPic()+"', '"+e.getReportedTime()
				+"', '"+e.getUserID()+"', '"+e.getDutyNum()+"', '"+e.getBadRoadNum()+"', '"+e.getUnattendedNum()
				+"', '"+e.getSundriesNum()+"', '"+e.getDenoterNum()+"', '"+e.getStatus()+"', '"+e.getOther()
				+"', '1', '"+e.getRemark()+"' )";
		try {
			PreparedStatement pstate = conn.prepareStatement(sql);
			System.out.println(sql);
			int rs = pstate.executeUpdate();
			if (rs==1) {
				System.out.println("事件添加成功");
			}
		} catch (SQLException e1) {
			System.out.println("事件添加失败");
			e1.printStackTrace();
		}
	}
}
