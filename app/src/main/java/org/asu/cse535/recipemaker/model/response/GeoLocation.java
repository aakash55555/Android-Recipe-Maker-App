package org.asu.cse535.recipemaker.model.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeoLocation implements Serializable {

    @SerializedName("lat")
    @Expose
    double lat;

    @SerializedName("lon")
    @Expose
    double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public GeoLocation(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
