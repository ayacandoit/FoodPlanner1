package com.example.foodplanner;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.Recipe;

public class MealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.meal);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        if (recipe != null) {

            TextView textView = findViewById(R.id.recipeName);
            ImageView imageView = findViewById(R.id.mealImage);
            TextView categoryTextView = findViewById(R.id.DishName);
            TextView areaTextView = findViewById(R.id.Description);

            textView.setText(recipe.getStrMeal());
            categoryTextView.setText("Category: " + recipe.getStrCategory());
            areaTextView.setText("Area: " + recipe.getStrArea());


            Glide.with(this).load(recipe.getStrMealThumb()).into(imageView);
        }
    }
}
