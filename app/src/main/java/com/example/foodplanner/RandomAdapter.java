package com.example.foodplanner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.getViewHolder> {
    ArrayList<Recipe>recipes;
    Activity activity;

    public RandomAdapter(ArrayList<Recipe> recipes, Activity activity) {
        this.recipes = recipes;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RandomAdapter.getViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View v=inflater.inflate(R.layout.random_recipe_item,parent,false);
        getViewHolder holder=new getViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RandomAdapter.getViewHolder holder, int position) {
        holder.textView.setText(recipes.get(position).getName());
        holder.imageView.setImageResource(recipes.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class getViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public getViewHolder(@NonNull View itemView) {

            super(itemView);
            textView=itemView.findViewById(R.id.recipeName);
            imageView=itemView.findViewById(R.id.cardImage);
        }
    }
}
