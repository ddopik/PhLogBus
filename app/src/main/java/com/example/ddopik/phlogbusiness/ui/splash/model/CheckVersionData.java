package com.example.ddopik.phlogbusiness.ui.splash.model;

import com.google.gson.annotations.SerializedName;

public class CheckVersionData{

	public static final int STATUS_NO_UPDATES = 0;
	public static final int STATUS_OPTIONAL_UPDATE = 1;
	public static final int STATUS_REQUIRED_UPDATES = 2;

	@SerializedName("min")
	private String min;

	@SerializedName("status")
	private int status;

	@SerializedName("latest")
	private String latest;

	public void setMin(String min){
		this.min = min;
	}

	public String getMin(){
		return min;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setLatest(String latest){
		this.latest = latest;
	}

	public String getLatest(){
		return latest;
	}
}