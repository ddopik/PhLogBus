package com.example.ddopik.phlogbusiness.ui.setupbrand.model;

import com.google.gson.annotations.SerializedName;

public class RequestVerificationResponse{

	@SerializedName("msg")
	private String msg;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}
}