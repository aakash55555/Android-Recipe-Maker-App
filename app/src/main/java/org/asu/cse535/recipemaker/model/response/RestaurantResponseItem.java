package org.asu.cse535.recipemaker.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RestaurantResponseItem  implements Serializable {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("morePages")
    @Expose
    private boolean morePages;

    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;

    @SerializedName("pages")
    @Expose
    private Integer totalPages;

    @SerializedName("data")
    @Expose
    private List<Restaurant> data = null;

    public RestaurantResponseItem(Integer page, boolean morePages, Integer totalResults, Integer totalPages, List<Restaurant> data) {
        this.page = page;
        this.morePages = morePages;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public boolean isMorePages() {
        return morePages;
    }

    public void setMorePages(boolean morePages) {
        this.morePages = morePages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Restaurant> getData() {
        return data;
    }

    public void setData(List<Restaurant> data) {
        this.data = data;
    }
}
