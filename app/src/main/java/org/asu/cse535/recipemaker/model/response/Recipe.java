package org.asu.cse535.recipemaker.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import java.io.Serializable;

public class Recipe implements Serializable {

    @SerializedName("recipe_id")
    @Expose
    String id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("image_url")
    @Expose
    String imageURL;

    @SerializedName("source_url")
    @Expose
    String sourceURL;

    @SerializedName("publisher")
    @Expose
    String publisher;

    @SerializedName("ingredients")
    @Expose
    ArrayList<String> ingredients;


    public Recipe(String id, String title, String imageURL, String sourceURL, String publisher, ArrayList<String> ingredients) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.sourceURL = sourceURL;
        this.publisher= publisher;
        this.ingredients = ingredients;
    }

    public Recipe(String id, String title, String imageURL, String sourceURL, String publisher) {
        this.id = id;
        this.title = title;
        this.imageURL = imageURL;
        this.sourceURL = sourceURL;
        this.publisher= publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
