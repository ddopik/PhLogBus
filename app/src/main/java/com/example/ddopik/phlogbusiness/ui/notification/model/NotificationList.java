package com.example.ddopik.phlogbusiness.ui.notification.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotificationList {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("entity_id")
    @Expose
    public Integer entityId;
    @SerializedName("is_read")
    @Expose
    public Boolean isRead;
    @SerializedName("photographer")
    @Expose
    public Photographer photographer;
    @SerializedName("campaign")
    @Expose
    public Campaign campaign;
    @SerializedName("brand")
    @Expose
    public Business brand;
    @SerializedName("photo")
    @Expose
    public BaseImage photo;


    @SerializedName("popup")
    public int popup;
    @SerializedName("popup_image")
    public String popupImage;
}
