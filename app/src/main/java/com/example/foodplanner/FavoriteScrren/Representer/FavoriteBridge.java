package com.example.foodplanner.FavoriteScrren.Representer;

import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import java.util.List;

public interface FavoriteBridge {
    interface View {
        void showFavorites(List<Recipe> recipes);
        void showError(String message);
    }

    interface Presenter {
        void loadFavorites();
        void removeFromFavorites(String id);
    }
}
