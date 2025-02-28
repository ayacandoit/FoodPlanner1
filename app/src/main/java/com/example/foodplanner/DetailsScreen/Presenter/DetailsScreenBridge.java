package com.example.foodplanner.DetailsScreen.Presenter;

import com.example.foodplanner.HomeScreen.View.Model.Recipe;

public interface DetailsScreenBridge {

    interface View {
        void onMealDetailsSuccess(Recipe recipe);
        void onMealDetailsFailure(String message);
    }

    interface Presenter {
        void getMealDetails(String recipeId);
    }
}
