package com.example.ddopik.phlogbusiness.ui.cart.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CartResponse{

	@SerializedName("data")
	private List<CartItem> data;

	public void setData(List<CartItem> data){
		this.data = data;
	}

	public List<CartItem> getData(){
		return data;
	}
}