package com.example.baitaplon.request_response;

import com.example.baitaplon.entity.Media;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse {

    @SerializedName("body")
    @Expose
    private Media body;
    @SerializedName("message")
    @Expose
    private String message;

    public Media getBody() {
        return body;
    }

    public void setBody(Media body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
