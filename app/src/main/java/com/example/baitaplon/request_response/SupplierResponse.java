package com.example.baitaplon.request_response;

import com.example.baitaplon.entity.Category;
import com.example.baitaplon.entity.Supplier;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class SupplierResponse {
    @SerializedName("body")
    @Expose
    private ArrayList<Supplier> body;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<Supplier> getBody() {
        return body;
    }

    public void setBody(ArrayList<Supplier> body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
