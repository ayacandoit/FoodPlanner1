package com.example.foodplanner.Model.Network;

import com.example.foodplanner.AreaResponse;
import com.example.foodplanner.CategoryResponse;
import com.example.foodplanner.RecipeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public  interface RecipeApi {

    @GET("random.php")
    Call<RecipeResponse> getRandomRecipe();

    @GET("categories.php")
    Call<CategoryResponse> getCategories();
    @GET("list.php?a=list")
    Call<AreaResponse>getArea();








}
