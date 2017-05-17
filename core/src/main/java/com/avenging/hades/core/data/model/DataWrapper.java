package com.avenging.hades.core.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hades on 2017/5/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataWrapper<T> {

    @JsonProperty("code")
    public int mCode;
    @JsonProperty("status")
    public String mStatus;
    @JsonProperty("copyright")
    public String mCopyright;
    @JsonProperty("attributionText")
    public String mAttributionText;
    @JsonProperty("attributionHTML")
    public String mAttributionHTML;
    @JsonProperty("etag")
    public String eETag;
    @JsonProperty("data")
    public DataContainer<T> mData;


    public int getCode() {
        return mCode;
    }

    public void setCode(int mCode) {
        this.mCode = mCode;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String mCopyright) {
        this.mCopyright = mCopyright;
    }

    public String getAttributionText() {
        return mAttributionText;
    }

    public void setAttributionText(String mAttributionText) {
        this.mAttributionText = mAttributionText;
    }

    public String getAttributionHTML() {
        return mAttributionHTML;
    }

    public void setAttributionHTML(String mAttributionHTML) {
        this.mAttributionHTML = mAttributionHTML;
    }

    public String geteETag() {
        return eETag;
    }

    public void seteETag(String eETag) {
        this.eETag = eETag;
    }

    public DataContainer<T> getData() {
        return mData;
    }

    public void setData(DataContainer<T> mData) {
        this.mData = mData;
    }
}
