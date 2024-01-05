package com.example.baitaplon.request_response;

public class NotificationRequest {
    String title, content, fcmToken;
    Integer receiverType;

    public NotificationRequest(String title, String content, String fcmToken, Integer receiverType) {
        this.title = title;
        this.content = content;
        this.fcmToken = fcmToken;
        this.receiverType = receiverType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Integer getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Integer receiverType) {
        this.receiverType = receiverType;
    }
}
