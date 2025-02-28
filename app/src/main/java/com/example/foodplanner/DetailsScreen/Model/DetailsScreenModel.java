package com.example.foodplanner.DetailsScreen.Model;

import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import com.example.foodplanner.HomeScreen.View.Model.RecipeResponse;
import com.example.foodplanner.Model.Network.RecipeApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsScreenModel {

    private RecipeApi recipeApi;

    public DetailsScreenModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        recipeApi = retrofit.create(RecipeApi.class);
    }

    public interface OnDetailsScreenListener {
        void onSuccess(Recipe recipe);
        void onFailure(String errorMessage);
    }

    public void fetchMealDetails(String recipeId, OnDetailsScreenListener listener) {
        Call<RecipeResponse> call = recipeApi.getMealById(recipeId);

        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMeals() != null && !response.body().getMeals().isEmpty()) {
                    listener.onSuccess(response.body().getMeals().get(0));
                } else {
                    listener.onFailure("Failed to fetch recipe details");
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
