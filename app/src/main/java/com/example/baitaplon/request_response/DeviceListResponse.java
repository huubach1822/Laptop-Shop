package com.example.baitaplon.request_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DeviceListResponse {
    @SerializedName("body")
    @Expose
    private ArrayList<DeviceList> body;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<DeviceList> getBody() {
        return body;
    }

    public void setBody(ArrayList<DeviceList> body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
