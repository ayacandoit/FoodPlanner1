package com.example.foodplanner;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RandomRecipeReciclerV extends AppCompatActivity {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private RecyclerView recyclerView;
    private RandomAdapter adapter;
    private List<Recipe> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.random_recycler_view);

        recyclerView = findViewById(R.id.rrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new RandomAdapter(recipeList, this);
        recyclerView.setAdapter(adapter);

        fetchRecipes();
    }

    private void fetchRecipes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeApi api = retrofit.create(RecipeApi.class);

        for (int i = 0; i < 5; i++) {  // Fetch multiple random recipes
            retrofit.create(RecipeApi.class).getRandomRecipe().enqueue(new Callback<RecipeResponse>() {
                @Override
                public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        recipeList.addAll(response.body().meals);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(RandomRecipeReciclerV.this, "Failed to load recipes", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RecipeResponse> call, Throwable t) {
                    Log.e("API_ERROR", "Error: " + t.getMessage());
                    Toast.makeText(RandomRecipeReciclerV.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
