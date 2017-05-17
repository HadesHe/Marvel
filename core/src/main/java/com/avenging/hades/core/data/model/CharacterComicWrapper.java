package com.avenging.hades.core.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2017/5/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterComicWrapper {

    @JsonProperty("available")
    protected int mAvailable;
    @JsonProperty("collectionURI")
    protected String mCollectionUri;
    @JsonProperty("returned")
    protected int mReturned;
    @JsonProperty("items")
    public List<Comic> mItems=new ArrayList<>();

    public CharacterComicWrapper(){}

    public int getAvailable() {
        return mAvailable;
    }

    public void setAvailable(int mAvailable) {
        this.mAvailable = mAvailable;
    }

    public String getCollectionUri() {
        return mCollectionUri;
    }

    public void setCollectionUri(String mCollectionUri) {
        this.mCollectionUri = mCollectionUri;
    }

    public int getReturned() {
        return mReturned;
    }

    public void setReturned(int mReturned) {
        this.mReturned = mReturned;
    }

    public List<Comic> getItems() {
        return mItems;
    }

    public void setItems(List<Comic> mItems) {
        this.mItems = mItems;
    }
}
