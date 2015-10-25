package com.zacharytamas.often;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zacharytamas.often.adapters.TodayAdapter;
import com.zacharytamas.often.models.managers.HabitManager;
import com.zacharytamas.often.utils.Data;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class TodayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        this.setTitle(getString(R.string.activity_title_today));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO Add this back
        Data.addTestData(this, false);

        HabitManager habitManager = new HabitManager(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Should launch Add Habit Activity", Snackbar.LENGTH_LONG);
            }
        });

        TodayAdapter todayAdapter = new TodayAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todayAdapter);

        todayAdapter.refill(habitManager.getAvailableHabits());

    }
}
