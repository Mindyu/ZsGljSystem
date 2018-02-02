package com.zsglj.bean;

public class EventType {

	private String eventTypeID;			//事件类型ID
	private String eventTypeName;		//事件类型名称
	private int    status;				//存在状态
	private String remark;				//备注
	
	
	public String getEventTypeID() {
		return eventTypeID;
	}
	public void setEventTypeID(String eventTypeID) {
		this.eventTypeID = eventTypeID;
	}
	public String getEventTypeName() {
		return eventTypeName;
	}
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
