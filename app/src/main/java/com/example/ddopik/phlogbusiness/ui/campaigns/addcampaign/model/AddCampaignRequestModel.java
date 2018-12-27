package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCampaignRequestModel implements Parcelable {


    public String campaignName;
    public File campaignCoverPhoto;
    public String campaignDescription;
    public String campaignPrize;
    public String campaignStartData;
    public String campaignEnddata;
    public List<Tag> tagsList;

    public HashMap<String, String> getTags() {
        HashMap<String, String> tagsSelected = new HashMap<String, String>();
        for (int i = 0; i < tagsList.size(); i++) {
            tagsSelected.put("tags[" + i + "]", tagsList.get(i).name);
        }

        return tagsSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.campaignName);
        dest.writeSerializable(this.campaignCoverPhoto);
        dest.writeString(this.campaignDescription);
        dest.writeString(this.campaignPrize);
        dest.writeString(this.campaignStartData);
        dest.writeString(this.campaignEnddata);
        dest.writeList(this.tagsList);
    }

    public AddCampaignRequestModel() {
    }

    protected AddCampaignRequestModel(Parcel in) {
        this.campaignName = in.readString();
        this.campaignCoverPhoto = (File) in.readSerializable();
        this.campaignDescription = in.readString();
        this.campaignPrize = in.readString();
        this.campaignStartData = in.readString();
        this.campaignEnddata = in.readString();
        this.tagsList = new ArrayList<Tag>();
        in.readList(this.tagsList, Tag.class.getClassLoader());
    }

    public static final Parcelable.Creator<AddCampaignRequestModel> CREATOR = new Parcelable.Creator<AddCampaignRequestModel>() {
        @Override
        public AddCampaignRequestModel createFromParcel(Parcel source) {
            return new AddCampaignRequestModel(source);
        }

        @Override
        public AddCampaignRequestModel[] newArray(int size) {
            return new AddCampaignRequestModel[size];
        }
    };
}
