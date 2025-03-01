package com.example.foodplanner.Calander.Presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Calander.Model.Reciepe_calendar;
import com.example.foodplanner.FavoriteScrren.RecipeDao;
import com.example.foodplanner.FavoriteScrren.RecipeDatabase;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
