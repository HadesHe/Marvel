package com.avenging.hades.core.data.model;

import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
