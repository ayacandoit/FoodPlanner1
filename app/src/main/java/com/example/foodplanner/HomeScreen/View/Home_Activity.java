package com.example.foodplanner.HomeScreen.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodplanner.HomeScreen.Model.Area;
import com.example.foodplanner.HomeScreen.Model.Category;
import com.example.foodplanner.HomeScreen.Model.Recipe;
import com.example.foodplanner.HomeScreen.View.Adapter.AreaAdapter;
import com.example.foodplanner.HomeScreen.View.Adapter.CategoryAdapter;
import com.example.foodplanner.HomeScreen.View.Adapter.RandomAdapter;
import com.example.foodplanner.Model.Network.MealRemoteDataSource;
import com.example.foodplanner.Model.Repositry;
import com.example.foodplanner.Presnter.AreaPresenter;
import com.example.foodplanner.Presnter.CategoryPresenter;
import com.example.foodplanner.Presnter.HomePresenter;
import com.example.foodplanner.R;
import com.example.foodplanner.View.AreaView;
import com.example.foodplanner.View.CategoryView;
import com.example.foodplanner.View.MealView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends AppCompatActivity implements MealView, CategoryView, AreaView {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private RecyclerView recyclerViewRecipes, recyclerViewCategories,recyclerViewArreaes;
    private RandomAdapter recipeAdapter;
    private CategoryAdapter categoryAdapter;
    private AreaAdapter areaAdapter;
    BottomNavigationView bottomNavigationView;

    private List<Recipe> recipeList = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private List<Area> areaList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.random_recycler_view);
        HomePresenter homePresenter =new HomePresenter(this, Repositry.getInstance(MealRemoteDataSource.getInstance()));
        CategoryPresenter categoryPresenter=new CategoryPresenter(Repositry.getInstance( MealRemoteDataSource.getInstance()),this);
        AreaPresenter areaPresenter=new AreaPresenter(Repositry.getInstance(MealRemoteDataSource.getInstance()),this);
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        recyclerViewRecipes = findViewById(R.id.rrv);
        recyclerViewCategories = findViewById(R.id.crv);
        recyclerViewArreaes=findViewById(R.id.arv);

        // Setup RecyclerViews
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recipeAdapter = new RandomAdapter(recipeList, this);
        recyclerViewRecipes.setAdapter(recipeAdapter);

        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(categoryList, this);
        recyclerViewCategories.setAdapter(categoryAdapter);


       recyclerViewArreaes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
       areaAdapter = new AreaAdapter( this);
       recyclerViewArreaes.setAdapter(areaAdapter);
        homePresenter.getRandomMeal();
        categoryPresenter.getAllCategory();
        areaPresenter.getAllArea();
        bottomNavigationView.setSelectedItemId(R.id.favotrite);





    }

    @Override
    public void setRandomMeal(List<Recipe> recipePojoList) {
        recipeAdapter.setData(recipePojoList);
        recipeAdapter.notifyDataSetChanged();

    }

    @Override
    public void setCategory(List<Category> categoryPojoList) {
        categoryAdapter.setCategoryList(categoryPojoList);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void setArea(List<Area> AreaPojoList) {
        areaAdapter.setAreaList(AreaPojoList);
        areaAdapter.notifyDataSetChanged();
    }

    @Override
    public void setErrorMessage(String errorMessage) {

    }

//    private void fetchRandomRecipes() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RecipeApi api = retrofit.create(RecipeApi.class);
//        api.getRandomRecipe().enqueue(new Callback<RecipeResponse>() {
//            @Override
//            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    recipeList.addAll(response.body().meals);
//                    recipeAdapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(RandomRecipeReciclerV.this, "Failed to load recipes", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RecipeResponse> call, Throwable t) {
//                Log.e("API_ERROR", "Error: " + t.getMessage());
//                Toast.makeText(RandomRecipeReciclerV.this, "Error fetching recipes", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void fetchCategories() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RecipeApi api = retrofit.create(RecipeApi.class);
//        api.getCategories().enqueue(new Callback<CategoryResponse>() {
//            @Override
//            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    categoryList.addAll(response.body().categories);
//                    categoryAdapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(RandomRecipeReciclerV.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoryResponse> call, Throwable t) {
//                Log.e("API_ERROR", "Error: " + t.getMessage());
//                Toast.makeText(RandomRecipeReciclerV.this, "Error fetching categories", Toast.LENGTH_SHORT).show();
//            }
//        });
   // }


}