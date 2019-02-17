package com.example.ddopik.phlogbusiness.ui.album.model;

import com.google.gson.annotations.SerializedName;

public class SavePhotoResponse {

	@SerializedName("msg")
	private String msg;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}
}