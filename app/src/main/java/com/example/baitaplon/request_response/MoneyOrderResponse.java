package com.example.baitaplon.request_response;

public class MoneyOrderResponse {
    String body, message;

    public MoneyOrderResponse(String body, String message) {
        this.body = body;
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
