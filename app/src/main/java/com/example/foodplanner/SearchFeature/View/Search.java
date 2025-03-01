package com.example.foodplanner.SearchFeature.View;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Calander.Calander;

import com.example.foodplanner.FavoriteScrren.FavoriteScreen;
import com.example.foodplanner.FavoriteScrren.RecipeDatabase;

import com.example.foodplanner.HomeScreen.View.Model.Area;
import com.example.foodplanner.HomeScreen.View.Model.Category;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import com.example.foodplanner.HomeScreen.View.View.Adapter.AreaAdapter;
import com.example.foodplanner.HomeScreen.View.View.Adapter.CategoryAdapter;
import com.example.foodplanner.HomeScreen.View.View.Adapter.RandomAdapter;
import com.example.foodplanner.HomeScreen.View.View.Home_Activity;
import com.example.foodplanner.Login.View.LogIn;
import com.example.foodplanner.R;
import com.example.foodplanner.SearchFeature.Model.Iingrediants;
import com.example.foodplanner.SearchFeature.Presenter.SearchBridge;
import com.example.foodplanner.SearchFeature.Presenter.SearchPresenter;
import com.example.foodplanner.SearchFeature.View.Adaptor.IngrediantAdaptor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Search extends AppCompatActivity implements SearchBridge.View {

    private CategoryAdapter categoryAdapter;
    private AreaAdapter areaAdapter;
    private IngrediantAdaptor ingrediantAdaptor;
    private RandomAdapter randomAdapter;
    private SearchPresenter searchPresenter;
    private EditText searchEditText;
    private Button btnCategory, btnArea, btnIngredient;
    private RecyclerView caregoriesRecyclerView, countriesRecyclerView, ingrediantsRecyclerView,
            searchByCategoriesRecyclerView;
    private String searchType = "c";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_actvity);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setupBottomNavigation();

        searchEditText = findViewById(R.id.Search);
        btnCategory = findViewById(R.id.btnCategory);
        btnArea = findViewById(R.id.btnArea);
        btnIngredient = findViewById(R.id.btnIngredient);

        caregoriesRecyclerView = findViewById(R.id.caregoriesRecyclerView);
        countriesRecyclerView = findViewById(R.id.countriesRecyclerView);
        ingrediantsRecyclerView = findViewById(R.id.ingrediantsRecyclerView);
        searchByCategoriesRecyclerView = findViewById(R.id.SearchBycaregoriesRecyclerView);

        searchPresenter = new SearchPresenter(this);
        searchPresenter.GetCategories();

        btnCategory.setOnClickListener(view -> {
            searchPresenter.GetCategories();
            searchType = "c";
        });

        btnArea.setOnClickListener(view -> {
            searchPresenter.GetCountries();
            searchType = "a";
        });

        btnIngredient.setOnClickListener(view -> {
            searchPresenter.GetIngrediants();
            searchType = "i";
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    searchPresenter.SearchByCategory(searchType, searchEditText);
                } else {
                    searchByCategoriesRecyclerView.setVisibility(GONE);
                    caregoriesRecyclerView.setVisibility(VISIBLE);
                    countriesRecyclerView.setVisibility(GONE);
                    ingrediantsRecyclerView.setVisibility(GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void logoutUser() {
        RecipeDatabase.deleteDatabase(this);
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this, LogIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.favotrite) {
                startActivity(new Intent(this, com.example.foodplanner.FavoriteScrren.FavoriteScreen.class));
            } else if (itemId == R.id.Logout) {
                logoutUser();
            } else if (itemId == R.id.home) {
                startActivity(new Intent(this, com.example.foodplanner.HomeScreen.View.View.Home_Activity.class));
            } else if (itemId == R.id.calander) {
                startActivity(new Intent(this, Calander.class));
            } else if (itemId == R.id.search) {
                startActivity(new Intent(this, Search.class));
            }
            return true;
        });
    }

    @Override
    public void onCategoriesSuccess(List<Category> categoriesList) {
        showCategories(categoriesList);
    }

    @Override
    public void onCategoriesFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCountriesSuccess(List<Area> areaList) {
        showCountries(areaList);
    }

    @Override
    public void onCountriesFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIngrediantsSuccess(List<Iingrediants> ingrediantsList) {
        showIngredients(ingrediantsList);
    }

    @Override
    public void onIngrediantsFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchByCategoriesSuccess(List<Recipe> recipeList) {
        if (recipeList != null && !recipeList.isEmpty()) {
            showSearchByCategories(recipeList);
        } else {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSearchByCategoriesFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSearchByCategories(List<Recipe> recipeList) {
        searchByCategoriesRecyclerView.setVisibility(VISIBLE);
        caregoriesRecyclerView.setVisibility(GONE);
        countriesRecyclerView.setVisibility(GONE);
        ingrediantsRecyclerView.setVisibility(GONE);

        searchByCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        randomAdapter = new RandomAdapter(recipeList, this);
        searchByCategoriesRecyclerView.setAdapter(randomAdapter);
    }

    private void showCategories(List<Category> categoryList) {
        caregoriesRecyclerView.setVisibility(VISIBLE);
        countriesRecyclerView.setVisibility(GONE);
        ingrediantsRecyclerView.setVisibility(GONE);
        searchByCategoriesRecyclerView.setVisibility(GONE);

        caregoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoryAdapter = new CategoryAdapter(categoryList, this);
        caregoriesRecyclerView.setAdapter(categoryAdapter);
    }

    private void showCountries(List<Area> areaList) {
        countriesRecyclerView.setVisibility(VISIBLE);
        caregoriesRecyclerView.setVisibility(GONE);
        ingrediantsRecyclerView.setVisibility(GONE);
        searchByCategoriesRecyclerView.setVisibility(GONE);

        countriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        areaAdapter = new AreaAdapter(areaList, this);
        countriesRecyclerView.setAdapter(areaAdapter);
    }

    private void showIngredients(List<Iingrediants> ingrediantsList) {
        ingrediantsRecyclerView.setVisibility(VISIBLE);
        caregoriesRecyclerView.setVisibility(GONE);
        countriesRecyclerView.setVisibility(GONE);
        searchByCategoriesRecyclerView.setVisibility(GONE);

        ingrediantsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ingrediantAdaptor = new IngrediantAdaptor(ingrediantsList, this);
        ingrediantsRecyclerView.setAdapter(ingrediantAdaptor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
