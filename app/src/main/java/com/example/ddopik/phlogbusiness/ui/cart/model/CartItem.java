package com.example.ddopik.phlogbusiness.ui.cart.model;

import java.util.List;

import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;
import com.google.gson.annotations.SerializedName;

public class CartItem {

	@SerializedName("saves_count")
	private int savesCount;

	@SerializedName("caption")
	private String caption;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("filters")
	private String filters;

	@SerializedName("thumbnail_url")
	private String thumbnailUrl;

	@SerializedName("url")
	private String url;

	@SerializedName("tags")
	private List<Tag> tags;

	@SerializedName("likes_count")
	private int likesCount;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("rate")
	private float rate;

	@SerializedName("comments_count")
	private int commentsCount;

	@SerializedName("location")
	private String location;

	@SerializedName("photographer")
	private Photographer photographer;

	@SerializedName("id")
	private int id;

	public void setSavesCount(int savesCount){
		this.savesCount = savesCount;
	}

	public int getSavesCount(){
		return savesCount;
	}

	public void setCaption(String caption){
		this.caption = caption;
	}

	public String getCaption(){
		return caption;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setFilters(String filters){
		this.filters = filters;
	}

	public String getFilters(){
		return filters;
	}

	public void setThumbnailUrl(String thumbnailUrl){
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getThumbnailUrl(){
		return thumbnailUrl;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setTags(List<Tag> tags){
		this.tags = tags;
	}

	public List<Tag> getTags(){
		return tags;
	}

	public void setLikesCount(int likesCount){
		this.likesCount = likesCount;
	}

	public int getLikesCount(){
		return likesCount;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setRate(float rate){
		this.rate = rate;
	}

	public float getRate(){
		return rate;
	}

	public void setCommentsCount(int commentsCount){
		this.commentsCount = commentsCount;
	}

	public int getCommentsCount(){
		return commentsCount;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setPhotographer(Photographer photographer){
		this.photographer = photographer;
	}

	public Photographer getPhotographer(){
		return photographer;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}