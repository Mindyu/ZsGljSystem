package com.zsglj.action;

import com.opensymphony.xwork2.ActionSupport;

public class Test extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String message;
    @Override
    public String execute() throws Exception {
        message = " success ";
        return SUCCESS;
    }
    public String getMessage(){
        return message;
    }
}