package com.example.foodplanner.FavoriteScrren.Model;

import android.content.Context;

import com.example.foodplanner.HomeScreen.View.Model.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteRepository {
    private RecipeDao recipeDao;

    public FavoriteRepository(Context context) {
        RecipeDatabase db = RecipeDatabase.getInstance(context);
        recipeDao = db.recipeDao();
    }

    public Completable addToFavorites(Recipe recipe) {
        return recipeDao.insertFavorite(recipe)
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<Recipe>> getAllFavorites() {
        return recipeDao.getAllFavorites()
                .subscribeOn(Schedulers.io());
    }

    public Single<Integer> isFavorite(String id) {
        return recipeDao.isFavorite(id)
                .subscribeOn(Schedulers.io());
    }

    public Completable removeFromFavorites(String id) {
        return recipeDao.deleteById(id)
                .subscribeOn(Schedulers.io());
    }
}