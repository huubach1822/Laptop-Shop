package com.example.baitaplon.request_response;

import com.example.baitaplon.entity.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderListResponse {
    @SerializedName("body")
    @Expose
    private ArrayList<Order> body;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<Order> getBody() {
        return body;
    }

    public void setBody(ArrayList<Order> body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
