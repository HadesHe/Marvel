package com.avenging.hades.core.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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

    public String getImageUrl(int index){
        if(mImageList!=null&&mImageList.size()>=index){
            return mImageList.get(index).buildCompleteImagePath();
        }
        return "";
    }

    public ArrayList<String> getImageUrlList(){
        ArrayList<String> results=new ArrayList<>();
        if(mImageList!=null){
            for(int i=0;i<mImageList.size();i++){
                results.add(getImageUrl(i));
            }
        }
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o)return true;
        if(!(o instanceof Comic)) return false;
        Comic comic= (Comic) o;
        return mId!=null?mId.equals(comic.mId):comic.mId==null
                &&(mType!=null?mType.equals(comic.mType):comic.mType==null);
    }

    @Override
    public int hashCode() {
        int result=((mId!=null)?mId.hashCode():0);
        result=31*result+(mType!=null?mType.hashCode():0);
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mResourceUri);
        dest.writeParcelable(this.mThumbnail,flags);
        dest.writeTypedList(this.mImageList);
    }

    protected Comic(Parcel in){
        this.mId= (Integer) in.readValue(Integer.class.getClassLoader());
        this.mTitle=in.readString();
        this.mResourceUri=in.readString();
        this.mThumbnail=in.readParcelable(Image.class.getClassLoader());
        this.mImageList=in.createTypedArrayList(Image.CREATOR);
    }

    public static final Creator<Comic> CREATOR=new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel source) {
            return new Comic(source);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    public Integer getId(){
        return mId;
    }

    public void setId(Integer id){
        mId=id;
    }

    public String getTitle(){
        return mTitle;
    }

    public Object getDescrition(){
        return mDescription;
    }

    public void setDescription(Object description){
        mDescription=description;
    }

    public Integer getStartYear(){
        return mStartYear;
    }

    public void setStartYear(Integer startYear){
        mStartYear=startYear;
    }

    public Integer getEndYear(){
        return mEndYear;
    }

    public void setEndYear(Integer endYear){
        mEndYear=endYear;
    }

    public String getRating(){
        return mRating;
    }

    public void setRating(String rating){
        mRating=rating;
    }

    public String getType(){
        return mType;
    }

    public void setType(String type){
        mType=type;
    }

    public String getModified(){
        return mModified;
    }

    public void setModified(String modified){
        mModified=modified;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName=name;
    }

    public String getResourceUri(){
        return mResourceUri;
    }

    public void setReourceUri(){
        mResourceUri=mResourceUri;
    }


    public Image getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Image mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public List<Image> getImageList() {
        return mImageList;
    }

    public void setImageList(List<Image> mImageList) {
        this.mImageList = mImageList;
    }
}
