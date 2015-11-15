package com.zacharytamas.often.models.managers;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.zacharytamas.often.models.Habit;

import org.joda.time.DateTime;

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
        return Select.from(Habit.class)
                .where(Condition.prop("available_at").lt(new DateTime().toDate().getTime())
                ).list();
    }

    public List<Habit> getAllHabits() {
        return Select.from(Habit.class).list();
    }
}
