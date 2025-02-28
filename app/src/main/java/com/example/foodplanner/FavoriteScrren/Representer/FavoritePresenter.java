package com.example.foodplanner.FavoriteScrren.Representer;

import com.example.foodplanner.FavoriteScrren.Model.FavoriteRepository;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import java.util.List;

public class FavoritePresenter implements FavoriteBridge.Presenter {

    private FavoriteBridge.View view;
    private FavoriteRepository repository;

    public FavoritePresenter(FavoriteBridge.View view, FavoriteRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadFavorites() {
        repository.getAllFavorites().observeForever(recipes -> {
            if (recipes != null && !recipes.isEmpty()) {
                view.showFavorites(recipes);
            } else {
                view.showError("No favorites found.");
            }
        });
    }

    @Override
    public void removeFromFavorites(String id) {
        repository.removeFromFavorites(id);
    }
}
