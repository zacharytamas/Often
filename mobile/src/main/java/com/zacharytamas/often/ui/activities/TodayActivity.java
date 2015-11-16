package com.zacharytamas.often.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.zacharytamas.often.Application;
import com.zacharytamas.often.R;
import com.zacharytamas.often.adapters.ViewPagerAdapter;
import com.zacharytamas.often.ui.fragments.AllHabitsListFragment;
import com.zacharytamas.often.ui.fragments.HabitListBaseFragment;
import com.zacharytamas.often.ui.fragments.TodayHabitListFragment;
import com.zacharytamas.often.utils.Data;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class TodayActivity extends AppCompatActivity {

    private Application application;
    private Tracker tracker;

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        application = (Application) getApplication();
        tracker = application.getDefaultTracker();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Data.addTestData(this, false);

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
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new TodayHabitListFragment(), getString(R.string.tab_title_today));
        adapter.addFragment(new AllHabitsListFragment(), getString(R.string.tab_title_all_habits));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // Log to Analytics which screen is being shown.
                tracker.setScreenName("Today~" + adapter.getPageTitle(position));
                tracker.send(new HitBuilders.ScreenViewBuilder().build());

                HabitListBaseFragment fragment = (HabitListBaseFragment) adapter.getItem(position);
                fragment.update();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(adapter);
    }
}
