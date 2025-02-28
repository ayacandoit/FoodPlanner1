package com.example.foodplanner.FavoriteScrren.Model;



import androidx.lifecycle.LiveData;


import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import java.util.List;

public class FavoriteScreenModel {
    private FavoriteRepository repository;

    public FavoriteScreenModel(FavoriteRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Recipe>> getAllFavorites() {
        return repository.getAllFavorites();
    }

    public void addToFavorites(Recipe recipe) {
        repository.addToFavorites(recipe);
    }

    public void removeFromFavorites(String id) {
        repository.removeFromFavorites(id);
    }


}

