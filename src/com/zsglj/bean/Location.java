package com.zsglj.bean;

public class Location {
	
	private String locationID;     //镇区ID
	private String TownName;	   //镇区名称
	private String StreeName;	   //所属街道
	
	
	public String getLocationID() {
		return locationID;
	}
	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}
	public String getTownName() {
		return TownName;
	}
	public void setTownName(String townName) {
		TownName = townName;
	}
	public String getStreeName() {
		return StreeName;
	}
	public void setStreeName(String streeName) {
		StreeName = streeName;
	}
	
	
}
