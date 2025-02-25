package com.example.foodplanner.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Area;
import com.example.foodplanner.Category_Country_Page;
import com.example.foodplanner.Model.Category;
import com.example.foodplanner.R;

import java.util.ArrayList;
import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    private List<Area> areaList=new ArrayList<>();

    private Context context;
    public AreaAdapter( Context context) {

        this.context = context;
    }


    @NonNull
    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.area_item, parent, false);
        return new AreaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaAdapter.ViewHolder holder, int position) {
        Area area = areaList.get(position);
        holder.areaName.setText(area.getStrArea());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Category_Country_Page.class);
            intent.putExtra("country", area.getStrArea());
            context.startActivity(intent);
        });




    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView areaName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);





            areaName = itemView.findViewById(R.id.AreaName);

        }
    }
}
