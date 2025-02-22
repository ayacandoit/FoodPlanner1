package com.example.foodplanner;

import retrofit2.Call;
import retrofit2.http.GET;

public  interface RecipeApi {

    @GET("random.php")
    Call<RecipeResponse> getRandomRecipe();


}
