package com.example.foodplanner.DetailsScreen.Presenter;

import com.example.foodplanner.DetailsScreen.Model.DetailsScreenModel;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;

public class DetailsScreenPresenter implements DetailsScreenBridge.Presenter {

    private DetailsScreenBridge.View view;
    private DetailsScreenModel model;

    public DetailsScreenPresenter(DetailsScreenBridge.View view) {
        this.view = view;
        this.model = new DetailsScreenModel();
    }

    @Override
    public void getMealDetails(String recipeId) {
        model.fetchMealDetails(recipeId, new DetailsScreenModel.OnDetailsScreenListener() {
            @Override
            public void onSuccess(Recipe recipe) {
                view.onMealDetailsSuccess(recipe);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.onMealDetailsFailure(errorMessage);
            }
        });
    }
}
