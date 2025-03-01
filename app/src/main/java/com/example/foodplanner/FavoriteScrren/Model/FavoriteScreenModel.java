package com.example.foodplanner.FavoriteScrren.Model;


import com.example.foodplanner.HomeScreen.View.Model.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavoriteScreenModel {
    private FavoriteRepository repository;

    public FavoriteScreenModel(FavoriteRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<Recipe>> getAllFavorites() {
        return repository.getAllFavorites();
    }

    public void addToFavorites(Recipe recipe) {
        repository.addToFavorites(recipe);
    }

    public void removeFromFavorites(String id) {
        repository.removeFromFavorites(id);
    }


}

