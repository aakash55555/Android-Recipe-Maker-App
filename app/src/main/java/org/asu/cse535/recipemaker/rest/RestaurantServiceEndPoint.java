package org.asu.cse535.recipemaker.rest;


import org.asu.cse535.recipemaker.model.response.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RestaurantServiceEndPoint {

    @GET("/menuitems/search")
    Call<SearchResult> searchMenu(@Header("X-RapidAPI-Key") String apiKey,
                                                  @Query("lat") Double lat,
                                                  @Query("lon") Double lon,
                                                  @Query("distance") Double distance,
                                                  @Query("fields") String fields);

    @GET("/restaurants/search")
    Call<SearchResult> searchRestaurant(@Header("X-RapidAPI-Key") String apiKey,
                                        @Query("lat") Double lat,
                                        @Query("lon") Double lon,
                                        @Query("distance") Double distance,
                                        @Query("q") String q);


}
