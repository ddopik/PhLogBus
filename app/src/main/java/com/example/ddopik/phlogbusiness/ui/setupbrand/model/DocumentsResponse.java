package com.example.ddopik.phlogbusiness.ui.setupbrand.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DocumentsResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("data")
	private List<Doc> data;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setData(List<Doc> data){
		this.data = data;
	}

	public List<Doc> getData(){
		return data;
	}
}