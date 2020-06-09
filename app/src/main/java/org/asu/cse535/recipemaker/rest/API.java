package org.asu.cse535.recipemaker.rest;

import org.asu.cse535.recipemaker.config.Config;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static <T> T builder(Class<T> endpoint, String URL) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(endpoint);
    }

    public static RestaurantServiceEndPoint restaurants() {
        return builder(RestaurantServiceEndPoint.class,Config.RESTAURANT_API_BASE_URL);
    }

    public static RecipeServiceEndPoint recipes() {
        return builder(RecipeServiceEndPoint.class,Config.RECIPE_API_BASE_URL);
    }
}
