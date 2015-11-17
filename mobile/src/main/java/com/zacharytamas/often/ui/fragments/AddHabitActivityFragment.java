package com.zacharytamas.often.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zacharytamas.often.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddHabitActivityFragment extends Fragment {

    public AddHabitActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_habit, container, false);
    }
}
