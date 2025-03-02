package com.example.foodplanner.DetailsScreen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Calander.Model.Reciepe_calendar;
import com.example.foodplanner.Calander.Presenter.CalendarPresenter;
import com.example.foodplanner.Calander.View.Calander;
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
import com.example.foodplanner.SearchFeature.View.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealActivity extends AppCompatActivity implements DetailsScreenBridge.View {

    private TextView nameTextView, categoryTextView, areaTextView, instructionsTextView;
    private ImageView imageView;
    private RecyclerView ingredientsRecyclerView;
    private FavoriteRepository favoriteRepository;
    private WebView myWeb;
    private ImageView favoriteIcon,calendar;
    private BottomNavigationView bottomNavigationView;

    private Recipe recipe;
    private DetailsScreenBridge.Presenter presenter;
    CalendarPresenter calendarPresenter;

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
        calendar = findViewById(R.id.calendar);
        favoriteRepository = new FavoriteRepository(this);
        presenter = new DetailsScreenPresenter(this);
        calendarPresenter = new CalendarPresenter(this);
        String recipeId = getIntent().getStringExtra("recipeId");

        setupBottomNavigation();
        setupFavoriteIcon(recipeId);

        if (recipeId != null) {
            presenter.getMealDetails(recipeId);
        }

        calendar.setOnClickListener(view -> {
            openDatePicker();
        });
    }

    public void addreciepe(String date){
        Reciepe_calendar recipe = new Reciepe_calendar(this.recipe, date);
        Log.d("Calendar", "Selected date: " + new Date().toString());
        recipe.setRecipe(this.recipe);
        recipe.setDate(date);
        calendarPresenter.addToCalendar(recipe);
        Intent intent = new Intent(this, Calander.class);
        startActivity(intent);
    }
    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the selected date
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        Toast.makeText(MealActivity.this, "Selected Date: " +
                                selectedDate, Toast.LENGTH_SHORT).show();
                        addreciepe(selectedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();

    }
    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            FirebaseAuth auth = FirebaseAuth.getInstance();

            if (itemId == R.id.favotrite) {
                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(MealActivity.this, FavoriteScreen.class));
                } else {
                    Toast.makeText(this, "Please log in to access favorites", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LogIn.class));
                }
            } else if (itemId == R.id.Logout) {
                if (auth.getCurrentUser() != null) { // Check if user is logged in
                    // Log out the user
                    auth.signOut(); // Sign out from Firebase
                    RecipeDatabase.deleteDatabase(this); // Clear local database (if needed)
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LogIn.class));
                    finish();
                } else {
                    Toast.makeText(this, "You are not logged in", Toast.LENGTH_SHORT).show();
                }
            } else if (itemId == R.id.home) {
                startActivity(new Intent(MealActivity.this, Home_Activity.class));
            } else if (itemId == R.id.calander) {
                if (auth.getCurrentUser() != null) { // Check if user is logged in
                    startActivity(new Intent(MealActivity.this, Calander.class));
                } else {
                    Toast.makeText(this, "Please log in to access the calendar", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LogIn.class));
                }
            } else if (itemId == R.id.search) {
                startActivity(new Intent(MealActivity.this, Search.class));
            }
            return true;
        });
    }

    private void setupFavoriteIcon(String recipeId) {
        favoriteIcon.setOnClickListener(view -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            if (auth.getCurrentUser() != null) {
                favoriteRepository.isFavorite(recipeId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isFav -> {
                            if (isFav != null && isFav > 0) {
                                favoriteRepository.removeFromFavorites(recipeId)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                                        }, throwable -> {
                                       Toast.makeText(this, "Failed to remove from favorites", Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                favoriteRepository.addToFavorites(recipe)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                                        }, throwable -> {
                                            Toast.makeText(this, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        }, throwable -> {
                            Toast.makeText(this, "Failed to check favorite status", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "Please log in to add to favorites", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LogIn.class));
            }
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