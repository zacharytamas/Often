package com.zacharytamas.often.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zacharytamas.often.R;


/**
 *
 */
public class TodayHabitListFragment extends HabitListBaseFragment {

    public TodayHabitListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_today_habit_list, container, false);
        setupRecyclerView(view, true);
        update();
        return view;
    }

    public void update() {
        listAdapter.refill(habitManager.getAvailableHabits());
    }

}
