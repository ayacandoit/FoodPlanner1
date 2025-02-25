package com.example.foodplanner;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Model.Network.RecipeApi;
import com.example.foodplanner.Model.Recipe;
import com.example.foodplanner.View.RandomAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Category_Country_Page extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RandomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_country_page);
        recyclerView = findViewById(R.id.allrv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        String category = getIntent().getStringExtra("category");

        String country = getIntent().getStringExtra("country");

        if (category != null) {
            fetchRecipesByCategory(category);
        } else if (country != null) {
            fetchRecipesByArea(country);
        }
    }

    private void fetchRecipesByCategory(String category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeApi recipeApi = retrofit.create(RecipeApi.class);
        Call<RecipeResponse> call = recipeApi.getRecipesByCategory(category);

        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Recipe> recipes = response.body().getMeals();
                    adapter = new RandomAdapter(recipes,Category_Country_Page.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Toast.makeText(Category_Country_Page.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchRecipesByArea(String area) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeApi recipeApi = retrofit.create(RecipeApi.class);
        Call<RecipeResponse> call = recipeApi.getMealsByArea(area);

        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeResponse> call, @NonNull Response<RecipeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Recipe> recipes = response.body().getMeals();
                    adapter = new RandomAdapter(recipes,Category_Country_Page.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeResponse> call, @NonNull Throwable t) {
                Toast.makeText(Category_Country_Page.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
