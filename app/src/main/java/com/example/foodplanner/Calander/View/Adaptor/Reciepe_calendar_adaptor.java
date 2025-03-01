package com.example.foodplanner.Calander.View.Adaptor;

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
import com.example.foodplanner.Calander.Model.Reciepe_calendar;
import com.example.foodplanner.DetailsScreen.MealActivity;
import com.example.foodplanner.R;

import java.util.List;

public class Reciepe_calendar_adaptor extends RecyclerView.Adapter<Reciepe_calendar_adaptor.ViewHolder> {
    private List<Reciepe_calendar> recipeList;
    private Context context;

    public Reciepe_calendar_adaptor(List<Reciepe_calendar> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_reciepe_calendar_adaptor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reciepe_calendar recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.recipe.strMeal);


        Glide.with(context)
                .load(recipe.recipe.strMealThumb)
                .placeholder(R.drawable.re)
                .into(holder.recipeImage);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MealActivity.class);
            intent.putExtra("recipeId", recipe.getIdMeal());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setData(List<Reciepe_calendar> list) {
        this.recipeList = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        ImageView recipeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeImage = itemView.findViewById(R.id.cardImage);
        }
    }
}
