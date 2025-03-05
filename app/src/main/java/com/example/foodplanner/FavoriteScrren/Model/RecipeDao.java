package com.example.foodplanner.FavoriteScrren.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.Calander.Model.Reciepe_calendar;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavorite(Recipe recipe);

    @Query("SELECT * FROM favorite_recipes")
    Flowable<List<Recipe>> getAllFavorites();

    @Query("SELECT COUNT(*) FROM favorite_recipes WHERE idMeal = :id")
    Single<Integer> isFavorite(String id);

    @Query("DELETE FROM favorite_recipes WHERE idMeal = :id")
    Completable deleteById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserttoCalendar(Reciepe_calendar reciepeCalendar);

    @Query("SELECT * FROM calendar_recipes WHERE date = :date")
    LiveData<List<Reciepe_calendar>> getRecipesForDate(String date);

    @Query("SELECT * FROM calendar_recipes")
    LiveData<List<Reciepe_calendar>> getAllcalendar_recipes();
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    Completable inserttoCalendar(Reciepe_calendar reciepeCalendar);
//
//    @Query("SELECT * FROM calendar_recipes WHERE date = :date")
//    Flowable<List<Reciepe_calendar>> getRecipesForDate(String date);
//
//    @Query("SELECT * FROM calendar_recipes")
//    Flowable<List<Reciepe_calendar>> getAllcalendar_recipes();
}