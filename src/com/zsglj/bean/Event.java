package com.zsglj.bean;

public class Event {

	private String eventID;         //事件ID	
	private String CompanyID;		//管养单位ID
	private String typeID;			//事件类型ID
	private String eventDate;		//事件日期
	private String eventTime;		//事件发生时间
	private String roadLocation;	//路段位置
	private String eventCase;		//事件情况
	private String trafficCase;		//交通情况
	private String townID;			//所属镇区ID
	private String picture;			//现场图片
	private String finishTime;		//完成处置时间
	private String finishCase;		//完成处置情况
	private int    peopleNum;		//出动人数
	private int    carNum;			//出动车辆
	private String FinishPic;		//处置后图片
	private String reportedTime;	//上报时间
	private String userID;			//上报人ID
	private int    dutyNum;			//值班人数
	private int    badRoadNum;		//损坏路段数量
	private int    unattendedNum;	//处置值守数量
	private int    sundriesNum;		//清楚杂物数量
	private int    denoterNum;		//清除标牌数量
	private int    status;			//事件处理状态      1保存   2已提交   3已撤回               
	private String other;			//其他
	private String returnReason;	//打回理由
	private String revokeReason;	//申请撤回理由
	private int    existStatus;		//存在状态       1存在    0删除
	private String remark;			//备注
	
	
	public String getEventID() {
		return eventID;
	}
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
	public String getCompanyID() {
		return CompanyID;
	}
	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}
	public String getTypeID() {
		return typeID;
	}
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public String getRoadLocation() {
		return roadLocation;
	}
	public void setRoadLocation(String roadLocation) {
		this.roadLocation = roadLocation;
	}
	public String getEventCase() {
		return eventCase;
	}
	public void setEventCase(String eventCase) {
		this.eventCase = eventCase;
	}
	public String getTrafficCase() {
		return trafficCase;
	}
	public void setTrafficCase(String trafficCase) {
		this.trafficCase = trafficCase;
	}
	public String getTownID() {
		return townID;
	}
	public void setTownID(String townID) {
		this.townID = townID;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getFinishCase() {
		return finishCase;
	}
	public void setFinishCase(String finishCase) {
		this.finishCase = finishCase;
	}
	public int getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}
	public int getCarNum() {
		return carNum;
	}
	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}
	public String getFinishPic() {
		return FinishPic;
	}
	public void setFinishPic(String finishPic) {
		FinishPic = finishPic;
	}
	public String getReportedTime() {
		return reportedTime;
	}
	public void setReportedTime(String reportedTime) {
		this.reportedTime = reportedTime;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getDutyNum() {
		return dutyNum;
	}
	public void setDutyNum(int dutyNum) {
		this.dutyNum = dutyNum;
	}
	public int getBadRoadNum() {
		return badRoadNum;
	}
	public void setBadRoadNum(int badRoadNum) {
		this.badRoadNum = badRoadNum;
	}
	public int getUnattendedNum() {
		return unattendedNum;
	}
	public void setUnattendedNum(int unattendedNum) {
		this.unattendedNum = unattendedNum;
	}
	public int getSundriesNum() {
		return sundriesNum;
	}
	public void setSundriesNum(int sundriesNum) {
		this.sundriesNum = sundriesNum;
	}
	public int getDenoterNum() {
		return denoterNum;
	}
	public void setDenoterNum(int denoterNum) {
		this.denoterNum = denoterNum;
	}
	public int getExistStatus() {
		return existStatus;
	}
	public void setExistStatus(int existStatus) {
		this.existStatus = existStatus;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getReturnReason() {
		return returnReason;
	}
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}
	public String getRevokeReason() {
		return revokeReason;
	}
	public void setRevokeReason(String revokeReason) {
		this.revokeReason = revokeReason;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Event [eventID=" + eventID + ", CompanyID=" + CompanyID
				+ ", typeID=" + typeID + ", eventDate=" + eventDate
				+ ", eventTime=" + eventTime + ", roadLocation=" + roadLocation
				+ ", eventCase=" + eventCase + ", trafficCase=" + trafficCase
				+ ", townID=" + townID + ", picture=" + picture
				+ ", finishTime=" + finishTime + ", finishCase=" + finishCase
				+ ", peopleNum=" + peopleNum + ", carNum=" + carNum
				+ ", FinishPic=" + FinishPic + ", reportedTime=" + reportedTime
				+ ", userID=" + userID + ", dutyNum=" + dutyNum
				+ ", badRoadNum=" + badRoadNum + ", unattendedNum="
				+ unattendedNum + ", sundriesNum=" + sundriesNum
				+ ", denoterNum=" + denoterNum + ", Status="
				+ status + ", other=" + other + ", returnReason="
				+ returnReason + ", revokeReason=" + revokeReason + ", existStatus="
				+ existStatus + ", remark=" + remark + "]";
	}
	

}
