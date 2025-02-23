package com.example.foodplanner.Model.Network;

import com.example.foodplanner.Area;
import com.example.foodplanner.Model.Category;

import java.util.List;

public interface AreaNetworkCallBack {

        void onSuccessResultArea(List<Area> RecipeList);
        void onFailureResultArea (String errorMessage);

}
