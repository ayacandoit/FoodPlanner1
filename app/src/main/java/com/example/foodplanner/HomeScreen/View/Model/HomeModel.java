package com.example.foodplanner.HomeScreen.View.Model;

import android.util.Log;

import com.example.foodplanner.NetworkLayer.ApiClient;
import com.example.foodplanner.NetworkLayer.ApiInterface;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeModel {

    private final CompositeDisposable disposable = new CompositeDisposable();

    public void GetRecipe(OnHomeListener onHomeListener) {
        ApiInterface services = ApiClient.getClient().create(ApiInterface.class);
        Observable<RecipeResponse> api = services.getRandomRecipe();

        disposable.add(
                api.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (response != null && response.meals != null) {
                                        onHomeListener.onSuccessRecipe(response.meals);
                                    } else {
                                        onHomeListener.onFailure("Empty response");
                                    }
                                },
                                throwable -> {
                                    Log.e("API_ERROR", "Error: " + throwable.getMessage());
                                    onHomeListener.onFailure(throwable.getMessage());
                                }
                        )
        );
    }

    public void GetCategories(OnHomeListener onHomeListener) {
        ApiInterface services = ApiClient.getClient().create(ApiInterface.class);
        Observable<CategoryResponse> api = services.getCategories();

        disposable.add(
                api.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (response != null && response.categories != null) {
                                        onHomeListener.onSuccessCategory(response.categories);
                                    } else {
                                        onHomeListener.onFailure("Empty response");
                                    }
                                },
                                throwable -> {
                                    Log.e("API_ERROR", "Error: " + throwable.getMessage());
                                    onHomeListener.onFailure(throwable.getMessage());
                                }
                        )
        );
    }

    public void GetMealByCountry(OnHomeListener onHomeListener) {
        ApiInterface services = ApiClient.getClient().create(ApiInterface.class);
        Observable<AreaResponse> api = services.getAreaes();

        disposable.add(
                api.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (response != null && response.meals != null) {
                                        onHomeListener.onSuccessArea(response.meals);
                                    } else {
                                        onHomeListener.onFailure("Empty response");
                                    }
                                },
                                throwable -> {
                                    Log.e("API_ERROR", "Error: " + throwable.getMessage());
                                    onHomeListener.onFailure(throwable.getMessage());
                                }
                        )
        );
    }

    public void clear() {
        disposable.clear();
    }

    public interface OnHomeListener {
        void onSuccessRecipe(List<Recipe> RecipeList);
        void onSuccessCategory(List<Category> CategoriesList);
        void onSuccessArea(List<Area> AreaList);
        void onFailure(String errorMessage);
    }
}
