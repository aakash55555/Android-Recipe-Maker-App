package org.asu.cse535.recipemaker.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipePublisherResponse implements Serializable {

    @SerializedName("count")
    @Expose
    Integer count;

    @SerializedName("recipes")
    @Expose
    ArrayList<Recipe> recipes;

    public RecipePublisherResponse(Integer count, ArrayList<Recipe> recipes) {
        this.count = count;
        this.recipes = recipes;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
