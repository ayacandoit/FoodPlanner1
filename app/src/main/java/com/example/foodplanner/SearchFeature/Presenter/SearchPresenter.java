package com.example.foodplanner.SearchFeature.Presenter;

import android.widget.EditText;

import com.example.foodplanner.HomeScreen.View.Model.Area;
import com.example.foodplanner.HomeScreen.View.Model.Category;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import com.example.foodplanner.SearchFeature.Model.Iingrediants;
import com.example.foodplanner.SearchFeature.Model.SearchModel;

import java.util.List;

public class SearchPresenter implements SearchBridge.Presenter {

    private SearchBridge.View view;
    private SearchModel searchModel;

    public SearchPresenter(SearchBridge.View view) {
        this.view = view;
        this.searchModel = new SearchModel();
    }

    @Override
    public void GetCategories() {
        searchModel.getCategories(new SearchModel.OnHomeListener() {
            @Override
            public void onSuccessCategory(List<Category> categoriesList) {
                view.onCategoriesSuccess(categoriesList);
            }

            @Override
            public void onSuccessCountries(List<Area> areaList) {}

            @Override
            public void onSuccessIngrediants(List<Iingrediants> ingrediantsList) {}

            @Override
            public void onSuccessSearchByCategory(List<Recipe> recipeList) {}

            @Override
            public void onFailure(String errorMessage) {
                view.onCategoriesFailure(errorMessage);
            }
        });
    }

    @Override
    public void GetCountries() {
        searchModel.getCountries(new SearchModel.OnHomeListener() {
            @Override
            public void onSuccessCategory(List<Category> categoriesList) {}

            @Override
            public void onSuccessCountries(List<Area> areaList) {
                view.onCountriesSuccess(areaList);
            }

            @Override
            public void onSuccessIngrediants(List<Iingrediants> ingrediantsList) {}

            @Override
            public void onSuccessSearchByCategory(List<Recipe> recipeList) {}

            @Override
            public void onFailure(String errorMessage) {
                view.onCountriesFailure(errorMessage);
            }
        });
    }

    @Override
    public void GetIngrediants() {
        searchModel.getIngredients(new SearchModel.OnHomeListener() {
            @Override
            public void onSuccessCategory(List<Category> categoriesList) {}

            @Override
            public void onSuccessCountries(List<Area> areaList) {}

            @Override
            public void onSuccessIngrediants(List<Iingrediants> ingrediantsList) {
                view.onIngrediantsSuccess(ingrediantsList);
            }

            @Override
            public void onSuccessSearchByCategory(List<Recipe> recipeList) {}

            @Override
            public void onFailure(String errorMessage) {
                view.onIngrediantsFailure(errorMessage);
            }
        });
    }

    @Override
    public void SearchByCategory(String str, EditText searchEditText) {
        searchModel.searchByCategory(str, searchEditText, new SearchModel.OnHomeListener() {
            @Override
            public void onSuccessCategory(List<Category> categoriesList) {}

            @Override
            public void onSuccessCountries(List<Area> areaList) {}

            @Override
            public void onSuccessIngrediants(List<Iingrediants> ingrediantsList) {}

            @Override
            public void onSuccessSearchByCategory(List<Recipe> recipeList) {
                view.onSearchByCategoriesSuccess(recipeList);
            }

            @Override
            public void onFailure(String errorMessage) {
                view.onSearchByCategoriesFailure(errorMessage);
            }
        });
    }

    public void clear() {
        searchModel.clear();
    }






}