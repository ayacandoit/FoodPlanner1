package com.example.foodplanner.Country_Area_Page;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Calander.Calander;
import com.example.foodplanner.FavoriteScrren.FavoriteScreen;
import com.example.foodplanner.FavoriteScrren.RecipeDatabase;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import com.example.foodplanner.HomeScreen.View.View.Adapter.RandomAdapter;
import com.example.foodplanner.HomeScreen.View.View.Home_Activity;
import com.example.foodplanner.Login.View.LogIn;
import com.example.foodplanner.R;
import com.example.foodplanner.Country_Area_Page.Presenter.CategoryCountryPresenter;
import com.example.foodplanner.Country_Area_Page.Presenter.CategoryCountryBridge;
import com.example.foodplanner.SearchFeature.View.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Category_Country_Page extends AppCompatActivity implements CategoryCountryBridge.View {

    private RecyclerView recyclerView;
    private RandomAdapter adapter;
    private CategoryCountryPresenter presenter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_country_page);

        recyclerView = findViewById(R.id.allrv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        presenter = new CategoryCountryPresenter(this);

        String category = getIntent().getStringExtra("category");
        String country = getIntent().getStringExtra("country");

        if (category != null) {
            presenter.fetchRecipesByCategory(category);
        } else if (country != null) {
            presenter.fetchRecipesByArea(country);
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.favotrite) {
                startActivity(new Intent(this, FavoriteScreen.class));
            } else if (itemId == R.id.Logout) {
                logoutUser();
            } else if (itemId == R.id.home) {
                startActivity(new Intent(this, Home_Activity.class));
            } else if (itemId == R.id.calander) {
                startActivity(new Intent(this, Calander.class));
            } else if (itemId == R.id.search) {
                startActivity(new Intent(this, Search.class));
            }
            return true;
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

    @Override
    public void onRecipesByCategorySuccess(List<Recipe> recipes) {
        adapter = new RandomAdapter(recipes, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecipesByCategoryFailure(String errorMessage) {
        Toast.makeText(this, "Failed to load category data: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecipesByAreaSuccess(List<Recipe> recipes) {
        adapter = new RandomAdapter(recipes, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecipesByAreaFailure(String errorMessage) {
        Toast.makeText(this, "Failed to load area data: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}

