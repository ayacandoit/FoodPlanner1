package com.example.foodplanner.NetworkLayer;


import com.example.foodplanner.HomeScreen.View.Model.AreaResponse;
import com.example.foodplanner.HomeScreen.View.Model.CategoryResponse;
import com.example.foodplanner.HomeScreen.View.Model.RecipeResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("random.php")
    Observable<RecipeResponse> getRandomRecipe();

    @GET("categories.php")
    Observable<CategoryResponse> getCategories();
    @GET("list.php?a=list")
    Observable<AreaResponse>getAreaes();
    @GET("filter.php")
    Observable<RecipeResponse> getRecipesByCategory(@Query("c") String category);

    @GET("filter.php")
    Observable<RecipeResponse> getMealsByArea(@Query("a") String area);
    @GET("lookup.php")
    Observable<RecipeResponse> getMealById(@Query("i") String mealId);
}
