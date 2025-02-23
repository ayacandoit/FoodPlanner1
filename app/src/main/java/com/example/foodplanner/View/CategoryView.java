package com.example.foodplanner.View;

import com.example.foodplanner.Model.Category;

import java.util.List;

public interface CategoryView {
    void setCategory(List<Category> categoryPojoList);
    void setErrorMessage (String errorMessage);
}
