package com.zacharytamas.often.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zacharytamas.often.R;
import com.zacharytamas.often.adapters.ViewPagerAdapter;
import com.zacharytamas.often.ui.fragments.TodayHabitListFragment;
import com.zacharytamas.often.utils.Data;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class TodayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Data.addTestData(this, true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.tabViewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Should launch Add Habit Activity", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new TodayHabitListFragment(), getString(R.string.tab_title_today));

        viewPager.setAdapter(adapter);
    }
}
