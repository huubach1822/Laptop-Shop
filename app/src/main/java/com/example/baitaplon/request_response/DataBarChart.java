package com.example.baitaplon.request_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBarChart {
    @SerializedName("value")
    @Expose
    private Long value;
    @SerializedName("key")
    @Expose
    private Integer key;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
