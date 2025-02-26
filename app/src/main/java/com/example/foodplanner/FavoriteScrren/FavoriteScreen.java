package com.example.foodplanner.FavoriteScrren;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.FavoriteScrren.FavoriteRepository;
import com.example.foodplanner.HomeScreen.Model.Recipe;
import com.example.foodplanner.HomeScreen.View.Adapter.RandomAdapter;
import com.example.foodplanner.R;
import java.util.List;

public class FavoriteScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RandomAdapter adapter;
    private FavoriteRepository favoriteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite_screen);

        recyclerView = findViewById(R.id.allrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoriteRepository = new FavoriteRepository(this);
        adapter = new RandomAdapter(List.of(), this);
        recyclerView.setAdapter(adapter);

        favoriteRepository.getAllFavorites().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setData(recipes);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
