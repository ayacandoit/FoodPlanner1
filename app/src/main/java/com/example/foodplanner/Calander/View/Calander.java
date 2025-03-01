package com.example.foodplanner.Calander.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Calander.Model.Reciepe_calendar;
import com.example.foodplanner.Calander.Presenter.CalendarPresenter;
import com.example.foodplanner.Calander.View.Adaptor.Reciepe_calendar_adaptor;
import com.example.foodplanner.FavoriteScrren.RecipeDatabase;
import com.example.foodplanner.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

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
    public Reciepe_calendar_adaptor adapter;
    RecyclerView reciepe_rec;
    private CalendarPresenter calendarPresenter;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calander);
        calendarView = findViewById(R.id.compactcalendar_view);
        Monthtxt = findViewById(R.id.Monthtxt);
        reciepe_rec = findViewById(R.id.reciepe_rec);
        calendarView.setUseThreeLetterAbbreviation(true);
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.displayOtherMonthDays(false);
        recipeDatabase = RecipeDatabase.getInstance(this);
        calendarPresenter = new CalendarPresenter(this);

        SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
        Monthtxt.setText(String.valueOf(dateFormatForMonth.format(calendarView.getFirstDayOfCurrentMonth())));

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                date = dateClicked;
                Log.d("Calendar", "Selected date: " + dateClicked.toString());
//                calendarPresenter.getRecipesForDate(date.toString())
//                        .observe(Calander.this, new Observer<List<Reciepe_calendar>>() {
//
//                            @Override
//                            public void onChanged(List<Reciepe_calendar> recipes) {
//                                Log.d("Calendar", "Recipes for date: " + recipes.toString());
//                                if (recipes.isEmpty() || recipes == null) {
//
//                                } else {
//                                    showReciepe(recipes);
//                                }
//
//                            }
//                        });
                calendarPresenter.getAllReciepe_calendar().observe(Calander.this,
                        new Observer<List<Reciepe_calendar>>() {

                            @Override
                            public void onChanged(List<Reciepe_calendar> recipes) {
                                Log.d("Calendar", "Recipes for date: " + recipes.toString());
                                Log.d("Calendar", "Selected date: " + date.toString());
                                if (recipes != null) {
                                    showReciepe(recipes);
                                } else {

                                }
                            }
                        });
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Monthtxt.setText(String.valueOf(dateFormatForMonth.format(firstDayOfNewMonth)));
            }
        });


    }

    private void showReciepe(List<Reciepe_calendar> recipeList) {
        reciepe_rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        adapter = new Reciepe_calendar_adaptor(recipeList, this);
        reciepe_rec.setAdapter(adapter);
    }


}