package org.asu.cse535.recipemaker.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RecipeResponse implements Serializable {

    @SerializedName("recipe")
    @Expose
    Recipe recipe;

    public RecipeResponse(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
