package com.example.foodplanner.Country_Area_Page.Presenter;

import com.example.foodplanner.Login.Presenter.Bridge;



import com.example.foodplanner.Country_Area_Page.Model.CategoryCountryModel;

import com.example.foodplanner.HomeScreen.View.Model.Recipe;

import java.util.List;

public class CategoryCountryPresenter implements CategoryCountryBridge.Presenter {
    private CategoryCountryBridge.View view;
    private CategoryCountryModel categoryCountryModel;

    public CategoryCountryPresenter(CategoryCountryBridge.View view) {
        this.view = view;
        this.categoryCountryModel = new CategoryCountryModel();
    }

    @Override
    public void fetchRecipesByCategory(String category) {
        categoryCountryModel.getRecipesByCategory(category, new CategoryCountryModel.OnCategoryCountryListener() {
            @Override
            public void onSuccess(List<Recipe> recipeList) {
                view.onRecipesByCategorySuccess(recipeList);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.onRecipesByCategoryFailure(errorMessage);
            }
        });
    }

    @Override
    public void fetchRecipesByArea(String area) {
        categoryCountryModel.getRecipesByArea(area, new CategoryCountryModel.OnCategoryCountryListener() {
            @Override
            public void onSuccess(List<Recipe> recipeList) {
                view.onRecipesByAreaSuccess(recipeList);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.onRecipesByAreaFailure(errorMessage);
            }
        });
    }
}