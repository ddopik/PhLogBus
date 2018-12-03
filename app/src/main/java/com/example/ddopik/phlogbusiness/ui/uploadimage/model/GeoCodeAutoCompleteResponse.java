package com.example.ddopik.phlogbusiness.ui.uploadimage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/25/2018.
 */
public class GeoCodeAutoCompleteResponse {


    public class Example {

        @SerializedName("predictions")
        @Expose
        public List<Prediction> predictions = null;
        @SerializedName("status")
        @Expose
        public String status;

    }


    public class MainTextMatchedSubstring {

        @SerializedName("length")
        @Expose
        public Integer length;
        @SerializedName("offset")
        @Expose
        public Integer offset;

    }


    public class MatchedSubstring {

        @SerializedName("length")
        @Expose
        public Integer length;
        @SerializedName("offset")
        @Expose
        public Integer offset;

    }

    public class Prediction {

        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("matched_substrings")
        @Expose
        public List<MatchedSubstring> matchedSubstrings = null;
        @SerializedName("place_id")
        @Expose
        public String placeId;
        @SerializedName("reference")
        @Expose
        public String reference;
        @SerializedName("structured_formatting")
        @Expose
        public StructuredFormatting structuredFormatting;
        @SerializedName("terms")
        @Expose
        public List<Term> terms = null;
        @SerializedName("types")
        @Expose
        public List<String> types = null;

    }

    public class StructuredFormatting {

        @SerializedName("main_text")
        @Expose
        public String mainText;
        @SerializedName("main_text_matched_substrings")
        @Expose
        public List<MainTextMatchedSubstring> mainTextMatchedSubstrings = null;
        @SerializedName("secondary_text")
        @Expose
        public String secondaryText;


    }

    public class Term {

        @SerializedName("offset")
        @Expose
        public Integer offset;
        @SerializedName("value")
        @Expose
        public String value;

    }
}