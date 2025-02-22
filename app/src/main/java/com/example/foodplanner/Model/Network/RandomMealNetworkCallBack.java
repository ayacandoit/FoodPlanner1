package com.example.foodplanner.Model.Network;

import com.example.foodplanner.Recipe;

import java.util.List;

public interface RandomMealNetworkCallBack {
    void onSuccessResult (List<Recipe> RecipeList);
    void onFailureResult (String errorMessage);
}
