package com.example.foodplanner.View;

import com.example.foodplanner.Area;
import com.example.foodplanner.Model.Category;

import java.util.List;

public interface AreaView {
    void setArea(List<Area> AreaPojoList);
    void setErrorMessage (String errorMessage);

}
