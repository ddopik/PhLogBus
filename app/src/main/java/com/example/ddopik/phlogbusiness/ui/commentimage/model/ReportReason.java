package com.example.ddopik.phlogbusiness.ui.commentimage.model;

import com.google.gson.annotations.SerializedName;

public class ReportReason {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("system_name")
	private String systemName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("deleted_at")
	private String deletedAt;

	public boolean selected;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setSystemName(String systemName){
		this.systemName = systemName;
	}

	public String getSystemName(){
		return systemName;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDeletedAt(String deletedAt){
		this.deletedAt = deletedAt;
	}

	public String getDeletedAt(){
		return deletedAt;
	}
}