package org.asu.cse535.recipemaker.model.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class SearchResult implements Serializable {

    @SerializedName("result")
    @Expose
    RestaurantResponseItem restaurantResponseItem;

    public SearchResult(RestaurantResponseItem restaurantResponseItem) {
        this.restaurantResponseItem = restaurantResponseItem;
    }

    public RestaurantResponseItem getRestaurantResponseItem() {
        return restaurantResponseItem;
    }

    public void setRestaurantResponseItem(RestaurantResponseItem restaurantResponseItem) {
        this.restaurantResponseItem = restaurantResponseItem;
    }
}
