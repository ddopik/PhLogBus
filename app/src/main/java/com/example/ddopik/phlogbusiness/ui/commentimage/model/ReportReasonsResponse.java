package com.example.ddopik.phlogbusiness.ui.commentimage.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ReportReasonsResponse{

	@SerializedName("data")
	private List<ReportReason> data;

	public void setData(List<ReportReason> data){
		this.data = data;
	}

	public List<ReportReason> getData(){
		return data;
	}
}