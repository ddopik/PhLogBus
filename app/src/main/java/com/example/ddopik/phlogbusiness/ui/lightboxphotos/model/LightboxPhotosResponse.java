package com.example.ddopik.phlogbusiness.ui.lightboxphotos.model;

import java.util.List;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.google.gson.annotations.SerializedName;

public class LightboxPhotosResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private List<BaseImage> data;

	@SerializedName("state")
	private String state;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(List<BaseImage> data){
		this.data = data;
	}

	public List<BaseImage> getData(){
		return data;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}
}