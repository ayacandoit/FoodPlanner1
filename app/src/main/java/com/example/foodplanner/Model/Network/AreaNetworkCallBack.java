package com.example.foodplanner.Model.Network;

import com.example.foodplanner.HomeScreen.Model.Area;

import java.util.List;

public interface AreaNetworkCallBack {

        void onSuccessResultArea(List<Area> RecipeList);
        void onFailureResultArea (String errorMessage);

}
