package com.example.ddopik.phlogbusiness.ui.campaigns.inner.model;

import com.google.gson.annotations.SerializedName;

public class ChangeCampaignDateResponse {

	@SerializedName("msg")
	private String msg;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}
}