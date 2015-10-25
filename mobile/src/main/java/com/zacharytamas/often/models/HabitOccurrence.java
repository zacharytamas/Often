package com.zacharytamas.often.models;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class HabitOccurrence extends SugarRecord<HabitOccurrence> {
    public Habit habit;
    public int streakLength;
    public Date completedAt;
    public Date wasDueAt;
}
