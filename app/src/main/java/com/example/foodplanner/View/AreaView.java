package com.example.foodplanner.View;

import com.example.foodplanner.HomeScreen.Model.Area;

import java.util.List;

public interface AreaView {
    void setArea(List<Area> AreaPojoList);
    void setErrorMessage (String errorMessage);

}
