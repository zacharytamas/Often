package com.zacharytamas.often.models.managers;

import android.content.Context;

import com.zacharytamas.often.models.Habit;

import java.util.List;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class HabitManager {

    private final Context mContext;

    public HabitManager(Context context) {
        mContext = context;
    }

    public List<Habit> getAvailableHabits() {
        return Habit.listAll(Habit.class);
    }
}
