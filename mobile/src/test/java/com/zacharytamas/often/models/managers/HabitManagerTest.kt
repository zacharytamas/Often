package com.zacharytamas.often.models.managers

import com.zacharytamas.often.models.Habit
import junit.framework.Assert
import org.junit.Test

/**
 * Created by zacharytamas on 10/15/15.
 */
class HabitManagerTest {

    fun createHabit(): Habit {
        val habit = Habit();

        return habit
    }

    @Test
    fun test_habitCompletion() {

        val habit = this.createHabit();

//        Assert.assertTrue(false)
    }

}