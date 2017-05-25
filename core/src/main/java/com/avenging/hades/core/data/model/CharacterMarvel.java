package com.avenging.hades.core.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


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
    @JsonProperty(value = "series")
    private CharacterComicWrapper mSeries;
    @JsonProperty(value = "stories")
    private CharacterComicWrapper mStories;
    @JsonProperty(value = "events")
    private CharacterComicWrapper mEvents;
    @JsonProperty(value = "urls")
    private List<Url> mUrls;

    public CharacterMarvel(){}

    public String getImageUrl(){
        return mThumbnail.buildCompleteImagePath();
    }

    @Override
    public String toString() {
        return mName;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Image getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Image mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public CharacterComicWrapper getComics() {
        return mComics;
    }

    public void setComics(CharacterComicWrapper mComics) {
        this.mComics = mComics;
    }

    public CharacterComicWrapper getStories() {
        return mStories;
    }

    public void setStories(CharacterComicWrapper mStories) {
        this.mStories = mStories;
    }

    public CharacterComicWrapper getEvents() {
        return mEvents;
    }

    public void setEvents(CharacterComicWrapper mEvents) {
        this.mEvents = mEvents;
    }

    public List<Url> getUrls() {
        return mUrls;
    }

    public void setUrls(List<Url> mUrls) {
        this.mUrls = mUrls;
    }

    public CharacterComicWrapper getSeries() {
        return mSeries;
    }

    public void setSeries(CharacterComicWrapper mSeries) {
        this.mSeries = mSeries;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if(!(o instanceof CharacterMarvel)) return false;
        CharacterMarvel that= (CharacterMarvel) o;
        return mId==that.mId;
    }

    @Override
    public int hashCode() {
        return (int)(mId^(mId>>>32));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeParcelable(mThumbnail,flags);
        dest.writeList(mUrls);
    }

    protected CharacterMarvel(Parcel in){
        mId=in.readLong();
        mName=in.readString();
        mDescription=in.readString();
        mThumbnail=in.readParcelable(Image.class.getClassLoader());
        mUrls=new ArrayList<>();
        in.readList(mUrls,Url.class.getClassLoader());
    }

    public static final Parcelable.Creator<CharacterMarvel> CREATOR=new Parcelable.Creator<CharacterMarvel>(){

        @Override
        public CharacterMarvel createFromParcel(Parcel source) {
            return new CharacterMarvel(source);
        }

        @Override
        public CharacterMarvel[] newArray(int size) {
            return new CharacterMarvel[size];
        }
    };

}
