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
}
