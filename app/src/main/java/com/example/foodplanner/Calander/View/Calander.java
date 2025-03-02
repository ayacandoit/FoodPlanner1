package com.example.foodplanner.Calander.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Calander.Model.Reciepe_calendar;
import com.example.foodplanner.Calander.Presenter.CalendarPresenter;
import com.example.foodplanner.Calander.View.Adaptor.Reciepe_calendar_adaptor;
import com.example.foodplanner.FavoriteScrren.FavoriteScreen;
import com.example.foodplanner.FavoriteScrren.RecipeDatabase;

import com.example.foodplanner.HomeScreen.View.View.Home_Activity;
import com.example.foodplanner.Login.View.LogIn;
import com.example.foodplanner.R;
import com.example.foodplanner.SearchFeature.View.Search;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Calander extends AppCompatActivity {
    CompactCalendarView calendarView;
    TextView Monthtxt;
    Reciepe_calendar reciepeCalendar;
    RecipeDatabase recipeDatabase;
    Reciepe_calendar_adaptor adapter;
    RecyclerView reciepe_rec;
    private CalendarPresenter calendarPresenter;
    Date date;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander);

        // ربط العناصر بملف XML
        calendarView = findViewById(R.id.compactcalendar_view);
        Monthtxt = findViewById(R.id.Monthtxt);
        reciepe_rec = findViewById(R.id.reciepe_rec);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        calendarView.setUseThreeLetterAbbreviation(true);
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.displayOtherMonthDays(false);

        recipeDatabase = RecipeDatabase.getInstance(this);
        calendarPresenter = new CalendarPresenter(this);

        SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
        Monthtxt.setText(dateFormatForMonth.format(calendarView.getFirstDayOfCurrentMonth()));

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                date = dateClicked;
                Log.d("Calendar", "Selected date: " + dateClicked.toString());

                calendarPresenter.getAllReciepe_calendar().observe(Calander.this,
                        new Observer<List<Reciepe_calendar>>() {
                            @Override
                            public void onChanged(List<Reciepe_calendar> recipes) {
                                Log.d("Calendar", "Recipes for date: " + recipes.toString());
                                Log.d("Calendar", "Selected date: " + date.toString());
                                if (recipes != null) {
                                    showReciepe(recipes);
                                }
                            }
                        });
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Monthtxt.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        setupBottomNavigation();
    }

    private void showReciepe(List<Reciepe_calendar> recipeList) {
        reciepe_rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new Reciepe_calendar_adaptor(recipeList, this);
        reciepe_rec.setAdapter(adapter);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.favotrite) {
                startActivity(new Intent(this, FavoriteScreen.class));
            } else if (itemId == R.id.Logout) {
                logoutUser();
            } else if (itemId == R.id.home) {
                startActivity(new Intent(this, Home_Activity.class));
            } else if (itemId == R.id.calander) {
                startActivity(new Intent(this, Calander.class));
            } else if (itemId == R.id.search) {
                startActivity(new Intent(this, Search.class));
            }
            return true;
        });
    }

    private void logoutUser() {
        Log.d("Logout", "User logged out");
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
        finish();
    }
}
