package com.example.foodplanner.Model.Network;

import com.example.foodplanner.Category;
import com.example.foodplanner.Recipe;

import java.util.List;

public interface CategoryNetworkCallBack {
    void onSuccessResultCategory (List<Category> RecipeList);
    void onFailureResultCategory (String errorMessage);
}
