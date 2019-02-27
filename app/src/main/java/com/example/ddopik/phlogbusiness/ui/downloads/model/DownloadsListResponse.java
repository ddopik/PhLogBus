package com.example.ddopik.phlogbusiness.ui.downloads.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DownloadsListResponse {

	@SerializedName("data")
	private List<GroupItem> data;

	public void setData(List<GroupItem> data){
		this.data = data;
	}

	public List<GroupItem> getData(){
		return data;
	}
}