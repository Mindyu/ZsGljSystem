package com.zsglj.bean;

public class Company {

	private String companyID;       		//单位名称
	private String companyName;				//单位名
	private String comIntroduction;			//单位简介
	private String comLocation;				//单位地址
	private int    comType; 				//状态     1管养单位   2非管养单位
	private int    status; 					//状态     1存在   0被删除
	
	
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getComIntroduction() {
		return comIntroduction;
	}
	public void setComIntroduction(String comIntroduction) {
		this.comIntroduction = comIntroduction;
	}
	public String getComLocation() {
		return comLocation;
	}
	public void setComLocation(String comLocation) {
		this.comLocation = comLocation;
	}
	public int getComType() {
		return comType;
	}
	public void setComType(int comType) {
		this.comType = comType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
}
