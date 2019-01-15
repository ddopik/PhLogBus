package com.example.ddopik.phlogbusiness.ui.setupbrand.model;

import com.google.gson.annotations.SerializedName;

public class Doc {

	@SerializedName("is_required")
	private int isRequired;

	@SerializedName("system_name")
	private String systemName;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("uploaded_file")
	private Object uploadedFile;

	public int progress;

	public String path;

	public void setIsRequired(int isRequired){
		this.isRequired = isRequired;
	}

	public int getIsRequired(){
		return isRequired;
	}

	public void setSystemName(String systemName){
		this.systemName = systemName;
	}

	public String getSystemName(){
		return systemName;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUploadedFile(Object uploadedFile){
		this.uploadedFile = uploadedFile;
	}

	public Object getUploadedFile(){
		return uploadedFile;
	}
}