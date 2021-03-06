package com.avenging.hades.core.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hades on 2017/5/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Url implements Parcelable{

    @JsonProperty("type")
    protected String mType;

    @JsonProperty("url")
    protected String mUrl;

    public Url(){}

    public String getType(){
        return mType;
    }

    public void setType(String type){
        mType=type;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mType);
        dest.writeString(this.mUrl);
    }

    protected Url(Parcel in){
        this.mType=in.readString();
        this.mUrl=in.readString();
    }

    public static final Parcelable.Creator<Url> CREATOR=new Parcelable.Creator<Url>(){

        @Override
        public Url createFromParcel(Parcel source) {
            return new Url(source);
        }

        @Override
        public Url[] newArray(int size) {
            return new Url[size];
        }
    };


}
