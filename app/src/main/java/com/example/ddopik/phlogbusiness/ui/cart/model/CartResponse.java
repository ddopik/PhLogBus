package com.example.ddopik.phlogbusiness.ui.cart.model;

import java.util.List;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.google.gson.annotations.SerializedName;

public class CartResponse{

	@SerializedName("data")
	public List<BaseImage> data;

}