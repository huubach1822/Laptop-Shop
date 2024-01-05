package com.example.baitaplon.request_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDeviceRequest {
    @SerializedName("deviceKey")
    @Expose
    private String deviceKey;
    @SerializedName("firebaseToken")
    @Expose
    private String firebaseToken;
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("osType")
    @Expose
    private String osType;

    public AddDeviceRequest(String deviceKey, String firebaseToken, String appVersion, String osType) {
        this.deviceKey = deviceKey;
        this.firebaseToken = firebaseToken;
        this.appVersion = appVersion;
        this.osType = osType;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }
}
