package com.zacharytamas.often.models.managers;

import com.zacharytamas.often.models.Habit;

import org.junit.Test;

/**
 * Created by zacharytamas on 10/26/15.
 */
public class HabitManagerTest {

    private Habit createHabit() {
        Habit habit = new Habit();
        return habit;
    }

    @Test
    public void test_habitCompletion() {
        Habit habit = createHabit();
    }

}
