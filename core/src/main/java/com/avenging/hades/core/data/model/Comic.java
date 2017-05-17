package com.avenging.hades.core.data.model;

import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Hades on 2017/5/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comic implements Parcelable{

    @JsonProperty("id")
    private Integer mId;
    @JsonProperty("title")
    private String mTitle;
    @JsonProperty("description")
    private Object mDescription;
    @JsonProperty("startYear")
    private Integer mStartYear;
    @JsonProperty("endYear")
    private Integer mEndYear;
    @JsonProperty("rating")
    private String mRating;
    @JsonProperty("type")
    private String mType;
    @JsonProperty("modified")
    private String mModified;
    @JsonProperty("name")
    private String mName;
    @JsonProperty("resourceURI")
    private String mResourceUri;
    @JsonProperty(value = "thumbnail")
    private Image mThumbnail;
    @JsonProperty(value = "images")
    private List<Image> mImageList;

    public Comic(){}

    public String getThumbnailUri(){
        if(mThumbnail!=null){
            return mThumbnail.buildCompleteImagePath();
        }

        return "";
    }
}
