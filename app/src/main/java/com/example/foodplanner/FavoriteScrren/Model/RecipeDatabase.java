package com.example.foodplanner.FavoriteScrren.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanner.Calander.Model.Reciepe_calendar;
import com.example.foodplanner.HomeScreen.View.Model.Recipe;


@Database(entities = {Recipe.class, Reciepe_calendar.class}, version = 2, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {
public static String dataBaseName="recipe_database";
    private static volatile RecipeDatabase INSTANCE;

    public abstract RecipeDao recipeDao();

    public static RecipeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RecipeDatabase.class, dataBaseName)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void deleteDatabase(Context context){
        if(INSTANCE!=null){
            INSTANCE.close();
            INSTANCE=null;
        }
        context.deleteDatabase(dataBaseName);

    }
}

