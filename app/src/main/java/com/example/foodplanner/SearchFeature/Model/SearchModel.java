package com.example.foodplanner.SearchFeature.Model;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.core.util.Pair;

import com.example.foodplanner.HomeScreen.View.Model.Area;
import com.example.foodplanner.HomeScreen.View.Model.Category;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import com.example.foodplanner.HomeScreen.View.Model.RecipeResponse;
import com.example.foodplanner.NetworkLayer.ApiClient;
import com.example.foodplanner.NetworkLayer.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchModel {

    private CompositeDisposable disposables = new CompositeDisposable();

    public void getCategories(OnHomeListener onHomeListener) {
        ApiInterface services = ApiClient.getClient().create(ApiInterface.class);
        disposables.add(
                services.getCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                categoryResponse -> onHomeListener.onSuccessCategory(categoryResponse.categories),
                                throwable -> onHomeListener.onFailure(throwable.getMessage())
                        )
        );
    }

    public void getCountries(OnHomeListener onHomeListener) {
        ApiInterface services = ApiClient.getClient().create(ApiInterface.class);
        disposables.add(
                services.getAreaes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                areaResponse -> onHomeListener.onSuccessCountries(areaResponse.meals),
                                throwable -> onHomeListener.onFailure(throwable.getMessage())
                        )
        );
    }

    public void getIngredients(OnHomeListener onHomeListener) {
        ApiInterface services = ApiClient.getClient().create(ApiInterface.class);
        disposables.add(
                services.getIngrediant()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ingrediantResponse -> onHomeListener.onSuccessIngrediants(ingrediantResponse.meals),
                                throwable -> onHomeListener.onFailure(throwable.getMessage())
                        )
        );
    }

    public void searchByCategory(String str, EditText searchEditText, OnHomeListener onHomeListener) {
        ApiInterface services = ApiClient.getClient().create(ApiInterface.class);
        disposables.add(
                Observable.create(emitter -> {
                            searchEditText.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if (s != null && s.length() > 0) {
                                        emitter.onNext(s.toString().toLowerCase());
                                    } else {
                                        emitter.onNext("");
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {}
                            });
                        })
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .switchMap(query -> {
                            String searchQuery = (String) query;
                            if (str.equalsIgnoreCase("c")) {
                                return services.getRecipesByCategory(searchQuery)
                                        .map(response -> new Pair<>(response, searchQuery));
                            } else if (str.equalsIgnoreCase("a")) {
                                return services.getRecipesByArea(searchQuery)
                                        .map(response -> new Pair<>(response, searchQuery));
                            } else if (str.equalsIgnoreCase("i")) {
                                return services.getRecipesByingrediant(searchQuery)
                                        .map(response -> new Pair<>(response, searchQuery));
                            } else {
                                return Observable.empty();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pair -> {
                                    RecipeResponse searchResponse = pair.first;
                                    String searchQuery = pair.second;
                                    if (searchResponse != null && searchResponse.meals != null) {
                                        List<Recipe> filteredResults = new ArrayList<>();
                                        for (Recipe recipe : searchResponse.meals) {
                                            if (recipe.getStrMeal().toLowerCase().contains(searchQuery)) {
                                                filteredResults.add(recipe);
                                            }
                                        }
                                        onHomeListener.onSuccessSearchByCategory(filteredResults);
                                    } else {
                                        onHomeListener.onFailure("No results found");
                                    }
                                },
                                throwable -> onHomeListener.onFailure(throwable.getMessage())
                        )
        );
    }
    public void clear() {
        disposables.clear();
    }

    public interface OnHomeListener {
        void onSuccessCategory(List<Category> categoriesList);
        void onSuccessCountries(List<Area> areaList);
        void onSuccessIngrediants(List<Iingrediants> ingrediantsList);
        void onSuccessSearchByCategory(List<Recipe> recipeList);
        void onFailure(String errorMessage);
    }
}