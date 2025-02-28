package com.example.foodplanner.Country_Area_Page.Model;


import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import com.example.foodplanner.HomeScreen.View.Model.RecipeResponse;
import com.example.foodplanner.Model.Network.RecipeApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryCountryModel {

    private RecipeApi recipeApi;

    public CategoryCountryModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recipeApi = retrofit.create(RecipeApi.class);
    }

    public interface OnCategoryCountryListener {
        void onSuccess(List<Recipe> recipes);
        void onFailure(String errorMessage);
    }

    public void getRecipesByCategory(String category, OnCategoryCountryListener listener) {
        Call<RecipeResponse> call = recipeApi.getRecipesByCategory(category);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body().getMeals());
                } else {
                    listener.onFailure("Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void getRecipesByArea(String area, OnCategoryCountryListener listener) {
        Call<RecipeResponse> call = recipeApi.getMealsByArea(area);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body().getMeals());
                } else {
                    listener.onFailure("Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
