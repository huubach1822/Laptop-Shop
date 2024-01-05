package com.example.baitaplon.request_response;

import com.example.baitaplon.entity.Category;
import com.example.baitaplon.entity.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class CategoryResponse {

    @SerializedName("body")
    @Expose
    private ArrayList<Category> body;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<Category> getBody() {
        return body;
    }

    public void setBody(ArrayList<Category> body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
