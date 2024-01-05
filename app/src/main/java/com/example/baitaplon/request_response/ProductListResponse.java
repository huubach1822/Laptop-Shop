
package com.example.baitaplon.request_response;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.example.baitaplon.entity.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ProductListResponse {

    @SerializedName("body")
    @Expose
    private ArrayList<Product> body;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<Product> getBody() {
        return body;
    }

    public void setBody(ArrayList<Product> body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
