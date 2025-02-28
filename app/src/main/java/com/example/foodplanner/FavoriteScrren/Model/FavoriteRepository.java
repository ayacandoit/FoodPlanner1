package com.example.foodplanner.FavoriteScrren.Model;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.foodplanner.FavoriteScrren.RecipeDao;
import com.example.foodplanner.FavoriteScrren.RecipeDatabase;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import java.util.List;
import java.util.concurrent.Executors;

public class FavoriteRepository {
    private RecipeDao recipeDao;

    public FavoriteRepository(Context context) {
        RecipeDatabase db = RecipeDatabase.getInstance(context);
        recipeDao = db.recipeDao();
    }

    public void addToFavorites(Recipe recipe) {
        Executors.newSingleThreadExecutor().execute(() -> recipeDao.insertFavorite(recipe));
    }

    public LiveData<List<Recipe>> getAllFavorites() {
        return recipeDao.getAllFavorites();
    }

    public LiveData<Integer> isFavorite(String id) {
        return recipeDao.isFavorite(id);
    }

    public void removeFromFavorites(String id) {
        Executors.newSingleThreadExecutor().execute(() -> recipeDao.deleteById(id));
    }
}
