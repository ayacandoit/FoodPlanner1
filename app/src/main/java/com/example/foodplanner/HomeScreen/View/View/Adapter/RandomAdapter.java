package com.example.foodplanner.HomeScreen.View.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.DetailsScreen.MealActivity;
import com.example.foodplanner.FavoriteScrren.Model.FavoriteRepository;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;
import com.example.foodplanner.R;

import java.util.List;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {
    private List<Recipe> recipeList;
    private Context context;
    private boolean isFavoriteScreen;
    private FavoriteRepository favoriteRepository;

    public RandomAdapter(List<Recipe> recipeList, Context context, boolean isFavoriteScreen) {
        this.recipeList = recipeList;
        this.context = context;
        this.isFavoriteScreen = isFavoriteScreen;
        if (isFavoriteScreen) {
            favoriteRepository = new FavoriteRepository(context);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.random_recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.strMeal);

        Glide.with(context)
                .load(recipe.strMealThumb)
                .placeholder(R.drawable.re)
                .into(holder.recipeImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MealActivity.class);
            intent.putExtra("recipeId", recipe.getIdMeal());
            context.startActivity(intent);
        });

        // عرض زر الحذف فقط في شاشة المفضلة
        if (isFavoriteScreen) {
            holder.deleteIcon.setVisibility(View.VISIBLE);
            holder.deleteIcon.setOnClickListener(v -> {
                favoriteRepository.removeFromFavorites(recipe.idMeal);
                recipeList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, recipeList.size());
            });
        } else {
            holder.deleteIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setData(List<Recipe> list) {
        this.recipeList = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        ImageView recipeImage, deleteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeImage = itemView.findViewById(R.id.cardImage);
            deleteIcon = itemView.findViewById(R.id.deleteIcon); // زر الحذف الجديد
        }
    }
}
