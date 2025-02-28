package com.example.foodplanner.Country_Area_Page.Presenter;

import com.example.foodplanner.HomeScreen.View.Model.Recipe;

import java.util.List;



import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import java.util.List;

public interface CategoryCountryBridge {

    interface View {
        void onRecipesByCategorySuccess(List<Recipe> recipes);
        void onRecipesByCategoryFailure(String errorMessage);

        void onRecipesByAreaSuccess(List<Recipe> recipes);
        void onRecipesByAreaFailure(String errorMessage);
    }

    interface Presenter {
        void fetchRecipesByCategory(String category);
        void fetchRecipesByArea(String area);
    }
}