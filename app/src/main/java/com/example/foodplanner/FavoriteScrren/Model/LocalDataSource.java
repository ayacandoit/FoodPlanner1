package com.example.foodplanner.FavoriteScrren.Model;


import android.content.Context;

import com.example.foodplanner.HomeScreen.View.Model.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class LocalDataSource {
    private RecipeDao recipeDao;

    public LocalDataSource(Context context) {
        RecipeDatabase db = RecipeDatabase.getInstance(context);
        recipeDao = db.recipeDao();
    }

    public void insertFavorite(Recipe recipe) {
        recipeDao.insertFavorite(recipe);
    }

    public Flowable<List<Recipe>> getAllFavorites() {
        return recipeDao.getAllFavorites();
    }

    public void deleteById(String id) {
        recipeDao.deleteById(id);
    }
}

