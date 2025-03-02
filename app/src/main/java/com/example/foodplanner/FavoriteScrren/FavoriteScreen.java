package com.example.foodplanner.FavoriteScrren;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Calander.View.Calander;
import com.example.foodplanner.FavoriteScrren.Model.FavoriteRepository;
import com.example.foodplanner.FavoriteScrren.Representer.FavoriteBridge;
import com.example.foodplanner.FavoriteScrren.Representer.FavoritePresenter;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import com.example.foodplanner.HomeScreen.View.View.Adapter.RandomAdapter;
import com.example.foodplanner.HomeScreen.View.View.Home_Activity;
import com.example.foodplanner.Login.View.LogIn;
import com.example.foodplanner.R;
import com.example.foodplanner.SearchFeature.View.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class FavoriteScreen extends AppCompatActivity implements FavoriteBridge.View {
    private RecyclerView recyclerView;
    private RandomAdapter adapter;
    private FavoritePresenter presenter;
    private BottomNavigationView bottomNavigationView;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_screen);

        recyclerView = findViewById(R.id.allrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        adapter = new RandomAdapter(List.of(), this);
        recyclerView.setAdapter(adapter);

        FavoriteRepository repository = new FavoriteRepository(this);
        presenter = new FavoritePresenter(this, repository);
        presenter.loadFavorites();

        setupBottomNavigation();
    }

    @Override
    public void showFavorites(List<Recipe> recipes) {
        adapter.setData(recipes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.favotrite) {
                startActivity(new Intent(this, FavoriteScreen.class));
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
                startActivity(new Intent(this, Home_Activity.class));
            } else if (itemId == R.id.calander) {
                startActivity(new Intent(this, Calander.class));
            } else if (itemId == R.id.search) {
                startActivity(new Intent(this, Search.class));
            }
            return true;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}