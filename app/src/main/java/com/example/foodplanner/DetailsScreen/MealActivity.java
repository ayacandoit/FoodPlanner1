package com.example.foodplanner.DetailsScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Calander.Calander;
import com.example.foodplanner.DetailsScreen.Presenter.DetailsScreenBridge;
import com.example.foodplanner.DetailsScreen.Presenter.DetailsScreenPresenter;
import com.example.foodplanner.DetailsScreen.View.IngredientAdapter;
import com.example.foodplanner.FavoriteScrren.FavoriteScreen;
import com.example.foodplanner.FavoriteScrren.Model.FavoriteRepository;
import com.example.foodplanner.FavoriteScrren.RecipeDatabase;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import com.example.foodplanner.HomeScreen.View.View.Home_Activity;
import com.example.foodplanner.Login.View.LogIn;
import com.example.foodplanner.R;
import com.example.foodplanner.SearchScreen.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MealActivity extends AppCompatActivity implements DetailsScreenBridge.View {

    private TextView nameTextView, categoryTextView, areaTextView, instructionsTextView;
    private ImageView imageView;
    private RecyclerView ingredientsRecyclerView;
    private FavoriteRepository favoriteRepository;
    private WebView myWeb;
    private ImageView favoriteIcon;
    private BottomNavigationView bottomNavigationView;

    private Recipe recipe;
    private DetailsScreenBridge.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal);

        nameTextView = findViewById(R.id.recipeName);
        categoryTextView = findViewById(R.id.DishName);
        areaTextView = findViewById(R.id.Description);
        instructionsTextView = findViewById(R.id.instruction);
        imageView = findViewById(R.id.mealImage);
        ingredientsRecyclerView = findViewById(R.id.ingerdents);
        myWeb = findViewById(R.id.webView);
        favoriteIcon = findViewById(R.id.love);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        favoriteRepository = new FavoriteRepository(this);
        presenter = new DetailsScreenPresenter(this);

        String recipeId = getIntent().getStringExtra("recipeId");

        setupBottomNavigation();
        setupFavoriteIcon(recipeId);

        if (recipeId != null) {
            presenter.getMealDetails(recipeId);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.favotrite) {
                startActivity(new Intent(MealActivity.this, FavoriteScreen.class));
            } else if (itemId == R.id.Logout) {
                RecipeDatabase.deleteDatabase(this);
                SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(this, LogIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (itemId == R.id.home) {
                startActivity(new Intent(MealActivity.this, Home_Activity.class));
            } else if (itemId == R.id.calander) {
                startActivity(new Intent(MealActivity.this, Calander.class));
            } else if (itemId == R.id.search) {
                startActivity(new Intent(MealActivity.this, Search.class));
            }
            return true;
        });
    }

    private void setupFavoriteIcon(String recipeId) {
        favoriteIcon.setOnClickListener(view -> {
            favoriteRepository.isFavorite(recipeId).observe(this, isFav -> {
                if (isFav != null && isFav > 0) {
                    favoriteRepository.removeFromFavorites(recipe.idMeal);
                } else {
                    favoriteRepository.addToFavorites(recipe);
                }
            });
            startActivity(new Intent(this, FavoriteScreen.class));
        });
    }

    @Override
    public void onMealDetailsSuccess(Recipe recipe) {
        this.recipe = recipe;
        displayMealDetails(recipe);
    }

    @Override
    public void onMealDetailsFailure(String message) {
        Toast.makeText(this, "Failed to load meal details: " + message, Toast.LENGTH_SHORT).show();
    }

    private void displayMealDetails(Recipe recipe) {
        nameTextView.setText(recipe.getStrMeal());
        categoryTextView.setText("Category: " + recipe.getStrCategory());
        areaTextView.setText("Area: " + recipe.getStrArea());
        instructionsTextView.setText(recipe.getStrInstructions());
        Glide.with(this).load(recipe.getStrMealThumb()).into(imageView);

        List<String> ingredients = new ArrayList<>();
        List<String> measures = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            try {
                String ingredient = (String) Recipe.class.getMethod("getStrIngredient" + i).invoke(recipe);
                String measure = (String) Recipe.class.getMethod("getStrMeasure" + i).invoke(recipe);

                if (ingredient != null && !ingredient.trim().isEmpty()) {
                    ingredients.add(ingredient);
                    measures.add(measure);
                }
            } catch (Exception ignored) { }
        }

        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ingredientsRecyclerView.setAdapter(new IngredientAdapter(this, ingredients, measures));

        myWeb.getSettings().setJavaScriptEnabled(true);
        myWeb.setWebViewClient(new WebViewClient());
        myWeb.loadUrl(recipe.getStrYoutube());
    }
}
