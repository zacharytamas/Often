package com.zacharytamas.often.ui.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zacharytamas.often.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllHabitsListFragment extends HabitListBaseFragment {


    public AllHabitsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_list, container, false);
        setupRecyclerView(view, false, false);
        update();

        return view;
    }

    public void update() {
        listAdapter.refill(habitManager.getAllHabits());
    }
}
