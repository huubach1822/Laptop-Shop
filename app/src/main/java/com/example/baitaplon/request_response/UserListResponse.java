package com.example.baitaplon.request_response;

import com.example.baitaplon.entity.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserListResponse {

    @SerializedName("body")
    @Expose
    private ArrayList<User> body;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("totalRecord")
    @Expose
    private Integer totalRecord;

    public ArrayList<User> getBody() {
        return body;
    }

    public void setBody(ArrayList<User> body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

}

