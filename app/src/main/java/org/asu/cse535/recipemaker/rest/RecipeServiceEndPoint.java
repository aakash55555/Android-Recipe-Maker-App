package org.asu.cse535.recipemaker.rest;


import org.asu.cse535.recipemaker.model.response.RecipePublisherResponse;
import org.asu.cse535.recipemaker.model.response.RecipeResponse;
import org.asu.cse535.recipemaker.model.response.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RecipeServiceEndPoint {

    @GET("/api/search")
    Call<RecipePublisherResponse> searchRecipePublisher(@Query("key") String key,
                                                        @Query("q") String q);

    @GET("/api/get")
    Call<RecipeResponse> searchRecipe(@Query("key") String key,
                                      @Query("rId") String rId);


}
