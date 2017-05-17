package com.avenging.hades.core.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hades on 2017/5/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataContainer<T> {

    @JsonProperty("offset")
    protected Integer mOffset;

    @JsonProperty("limit")
    protected Integer mLimit;

    @JsonProperty("total")
    protected Integer mTotal;

    @JsonProperty("count")
    protected Integer mCount;

    @JsonProperty("results")
    protected T mResults;

    public DataContainer() {
    }

    public Integer getOffset() {
        return mOffset;
    }

    public void setOffset(Integer mOffset) {
        this.mOffset = mOffset;
    }

    public Integer getLimit() {
        return mLimit;
    }

    public void setLimit(Integer mLimit) {
        this.mLimit = mLimit;
    }

    public Integer getTotal() {
        return mTotal;
    }

    public void setTotal(Integer mTotal) {
        this.mTotal = mTotal;
    }

    public Integer getCount() {
        return mCount;
    }

    public void setCount(Integer mCount) {
        this.mCount = mCount;
    }

    public T getResults() {
        return mResults;
    }

    public void setResults(T mResults) {
        this.mResults = mResults;
    }
}
