package com.example.foodplanner;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {
    @SerializedName("categories")
    public List<Category> categories;
}
