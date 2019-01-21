package com.example.ddopik.phlogbusiness.ui.downloads.model;

import java.util.List;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("date")
	private String date;

	@SerializedName("humman_date")
	private String hummanDate;

	@SerializedName("photos")
	private List<BaseImage> photos;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setHummanDate(String hummanDate){
		this.hummanDate = hummanDate;
	}

	public String getHummanDate(){
		return hummanDate;
	}

	public void setPhotos(List<BaseImage> photos){
		this.photos = photos;
	}

	public List<BaseImage> getPhotos(){
		return photos;
	}
}