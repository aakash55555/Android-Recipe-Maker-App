package org.asu.cse535.recipemaker.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restaurant implements Serializable {

    @SerializedName("restaurant_name")
    @Expose
    public String restaurantName;

    @SerializedName("restaurant_id")
    @Expose
    Integer restaurantId;

    @SerializedName("restaurant_phone")
    @Expose
    String restaurantPhone;

    @SerializedName("address")
    @Expose
    Address address;

    @SerializedName("geo")
    @Expose
    GeoLocation geo;

    @SerializedName("url")
    private String url;
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    public Restaurant(String restaurantName, Integer restaurantId, String restaurantPhone, Address address, GeoLocation geo, String url, String thumbnailUrl) {
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.restaurantPhone = restaurantPhone;
        this.address = address;
        this.geo = geo;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public GeoLocation getGeo() {
        return geo;
    }

    public void setGeo(GeoLocation geo) {
        this.geo = geo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
