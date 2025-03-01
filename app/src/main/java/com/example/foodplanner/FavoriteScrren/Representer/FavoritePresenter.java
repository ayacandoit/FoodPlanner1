package com.example.foodplanner.FavoriteScrren.Representer;

import com.example.foodplanner.FavoriteScrren.Model.FavoriteRepository;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenter implements FavoriteBridge.Presenter {
    private FavoriteBridge.View view;
    private FavoriteRepository repository;
    private CompositeDisposable disposables = new CompositeDisposable();

    public FavoritePresenter(FavoriteBridge.View view, FavoriteRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadFavorites() {
        disposables.add(repository.getAllFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> {
                    if (recipes != null && !recipes.isEmpty()) {
                        view.showFavorites(recipes);
                    } else {
                        view.showError("No favorites found.");
                    }
                }, throwable -> {
                    view.showError(throwable.getMessage());
                }));
    }

    @Override
    public void removeFromFavorites(String id) {
        disposables.add(repository.removeFromFavorites(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    // Reload favorites after removal
                    loadFavorites();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                }));
    }

    public void dispose() {
        disposables.clear();
    }
}