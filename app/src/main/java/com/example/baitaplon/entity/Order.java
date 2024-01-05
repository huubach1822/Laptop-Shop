package com.example.baitaplon.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Order {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("ownerId")
    @Expose
    private Integer ownerId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("note")
    @Expose
    private Object note;
    @SerializedName("orderCarts")
    @Expose
    private ArrayList<OrderCart> orderCarts;
    @SerializedName("owner")
    @Expose
    private User owner;
    @SerializedName("totalPrice")
    @Expose
    private Integer totalPrice;
    @SerializedName("payDate")
    @Expose
    private Object payDate;
    @SerializedName("paymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("paymentStatus")
    @Expose
    private Integer paymentStatus;
    @SerializedName("paymentUrl")
    @Expose
    private Object paymentUrl;

    public Order(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public ArrayList<OrderCart> getOrderCarts() {
        return orderCarts;
    }

    public void setOrderCarts(ArrayList<OrderCart> orderCarts) {
        this.orderCarts = orderCarts;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Object getPayDate() {
        return payDate;
    }

    public void setPayDate(Object payDate) {
        this.payDate = payDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Object getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(Object paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

}
