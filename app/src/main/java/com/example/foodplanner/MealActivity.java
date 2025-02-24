package com.example.foodplanner;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.meal);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        if (recipe != null) {
            TextView nameTextView = findViewById(R.id.recipeName);
            TextView categoryTextView = findViewById(R.id.DishName);
            TextView areaTextView = findViewById(R.id.Description);
            TextView instructionsTextView = findViewById(R.id.instruction);
            ImageView imageView = findViewById(R.id.mealImage);
            RecyclerView ingredientsRecyclerView = findViewById(R.id.ingerdents);
            WebView myWeb = findViewById(R.id.webView);

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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            ingredientsRecyclerView.setLayoutManager(layoutManager);
            IngredientAdapter adapter = new IngredientAdapter(this, ingredients, measures);
            ingredientsRecyclerView.setAdapter(adapter);

           myWeb.getSettings().setJavaScriptEnabled(true);
           myWeb.setWebViewClient(new WebViewClient());
           String url =(recipe.getStrYoutube()+recipe.getStrYoutube());
           myWeb.loadUrl(url);
        }
    }
}
