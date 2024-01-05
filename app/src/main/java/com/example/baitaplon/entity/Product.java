
package com.example.baitaplon.entity;

import javax.annotation.Generated;

import com.example.baitaplon.util.Const;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("jsonschema2pojo")
public class Product implements Serializable {

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
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("mediaId")
    @Expose
    private Integer mediaId;
    @SerializedName("media")
    @Expose
    private Media media;
    @SerializedName("price")
    @Expose
    private Long price;
    @SerializedName("priceAfterDiscount")
    @Expose
    private Long priceAfterDiscount;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("mediaIds")
    @Expose
    private Object mediaIds;
    @SerializedName("remain")
    @Expose
    private Integer remain;
    @SerializedName("sold")
    @Expose
    private Integer sold;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("supplier_id")
    @Expose
    private Integer supplierId;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("supplier")
    @Expose
    private Supplier supplier;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("fileIds")
    @Expose
    private Object fileIds;
    @SerializedName("file")
    @Expose
    private Object file;
    @SerializedName("category")
    @Expose
    private Category category;

    public Product(String title, String description, Integer mediaId, Long price, Long priceAfterDiscount, Integer categoryId, Integer remain, Integer rating, Integer supplierId, Integer locationId) {
        this.title = title;
        this.description = description;
        this.mediaId = mediaId;
        this.price = price;
        this.priceAfterDiscount = priceAfterDiscount;
        this.categoryId = categoryId;
        this.remain = remain;
        this.rating = rating;
        this.supplierId = supplierId;
        this.locationId = locationId;
    }

    public Product(Integer id, String title, String description, String imageUrl, Long price, Integer remain, Supplier supplier) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.remain = remain;
        this.supplier = supplier;
    }

    public Product() {

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {

        String Url = imageUrl.replace("http://localhost:8081/", Const.HOST_URL);
        return Url;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(Long priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Object getMediaIds() {
        return mediaIds;
    }

    public void setMediaIds(Object mediaIds) {
        this.mediaIds = mediaIds;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getFileIds() {
        return fileIds;
    }

    public void setFileIds(Object fileIds) {
        this.fileIds = fileIds;
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
