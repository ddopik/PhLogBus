package com.example.ddopik.phlogbusiness.ui.setupbrand.model;

import com.google.gson.annotations.SerializedName;

public class UploadedFile{

	@SerializedName("url")
	private String url;

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}
}