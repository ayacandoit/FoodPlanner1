package com.example.foodplanner.Calander.Presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Calander.Model.Reciepe_calendar;
import com.example.foodplanner.FavoriteScrren.Model.RecipeDao;
import com.example.foodplanner.FavoriteScrren.Model.RecipeDatabase;

import java.util.List;

public class CalendarPresenter {

    private RecipeDao recipeDao;

    public CalendarPresenter(Context context) {
        RecipeDatabase db = RecipeDatabase.getInstance(context);
        recipeDao = db.recipeDao();
    }

    public void addToCalendar(Reciepe_calendar recipe) {
        new Thread(()->recipeDao.inserttoCalendar(recipe)).start();
    }

    public LiveData<List<Reciepe_calendar>> getRecipesForDate(String date) {
        return recipeDao.getRecipesForDate(date);
    }

    public LiveData<List<Reciepe_calendar>> getAllReciepe_calendar() {
        return recipeDao.getAllcalendar_recipes();
    }
}
