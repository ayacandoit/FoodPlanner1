package com.example.foodplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RandomRecipeReciclerV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.random_recycler_view);

        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Recipe 1", R.drawable.re));
        recipes.add(new Recipe("Recipe 2", R.drawable.re));
        recipes.add(new Recipe("Recipe 3", R.drawable.re));
        recipes.add(new Recipe("Recipe 4", R.drawable.re));
        recipes.add(new Recipe("Recipe 5", R.drawable.re));

        // Create adapter
        RandomAdapter randomAdapter = new RandomAdapter(recipes, this);

        // Bind RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rrv);

        // Set LayoutManager to Horizontal
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(randomAdapter);
    }
}
