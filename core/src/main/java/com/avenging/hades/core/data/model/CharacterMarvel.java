package com.avenging.hades.core.data.model;

import android.media.Image;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hades on 2017/5/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterMarvel implements Parcelable {

    @JsonProperty(value = "id")
    private long mId;
    @JsonProperty(value = "name")
    private String mName;
    @JsonProperty(value = "description")
    private String mDescription;
    @JsonProperty(value = "thumbnail")
    private Image mThumbnail;
    @JsonProperty(value = "comics")
    private CharacterComicWrapper mComics;
}
