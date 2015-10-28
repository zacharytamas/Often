package com.zacharytamas.often.models;

import com.zacharytamas.often.utils.Dates;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created by zacharytamas on 10/26/15.
 */
public class HabitTest {

    Habit habit;
    Date now;

    void setUp() {
        now = getDate(1992, 1, 31);
        DateTimeUtils.setCurrentMillisFixed(now.getTime());
        this.habit = new Habit();
        this.habit.availableAt = now;
        habit.repeatType = RepeatType.PERIODICAL;
        habit.repeatScalar = 1;
        habit.repeatUnit = RepeatUnit.DAILY;
    }

    private Date getDate(int year, int month, int date) {
        return new DateTime(year, month, date, 0, 0).toDate();
    }

    private void assertSameDay(Date d1, Date d2) {
        Assert.assertEquals(new DateTime(d1).withTimeAtStartOfDay(),
                            new DateTime(d2).withTimeAtStartOfDay());
    }

    @Test
    public void test_setRepeatOnWeekday() {
        this.setUp();

        assertThat((int) habit.repeatWeekdays, is(0));
        Assert.assertFalse(habit.getRepeatsOnWeekday(Calendar.SUNDAY));
        habit.setRepeatsOnWeekday(Calendar.SUNDAY, true);
        Assert.assertTrue(habit.getRepeatsOnWeekday(Calendar.SUNDAY));
        habit.setRepeatsOnWeekday(Calendar.SUNDAY, false);
        Assert.assertFalse(habit.getRepeatsOnWeekday(Calendar.SUNDAY));

        Assert.assertFalse(habit.getRepeatsOnWeekday(Calendar.MONDAY));
        habit.setRepeatsOnWeekday(Calendar.SUNDAY, true);
        habit.setRepeatsOnWeekday(Calendar.MONDAY, true);
        Assert.assertTrue(habit.getRepeatsOnWeekday(Calendar.SUNDAY));
    }

    @Test
    public void test_completeHabit_nextAvailableAt() {
        // The main logic of determining when the next occurrence is scheduled for is
        // covered by the tests for the Dates utils. To avoid testing the same stuff
        // really, I just make sure that the completeHabit() method sets that value
        // appropriately and it's what we expect. Not fully exhaustive of selecting
        // all the different repeatTypes, repeatUnits, repeatScalars, etc.

        this.setUp();

        habit.repeatScalar = 2;

        Date next = Dates.nextAvailableAt(habit, now);
        // Sanity. Assert that availableAt is not currently the next date so that
        // later when we assert that it is we know the change occurred.
        assertThat(habit.availableAt, is(not(next)));
        assertThat(habit.lastCompletedAt, is(not(now)));

        habit.completeHabit();

        assertThat(habit.availableAt, is(next));
        assertThat(habit.lastCompletedAt, is(now));

    }

    /**
     * Test that the dueAt property is appropriately updated when completing
     * a Habit that is required.
     */
    @Test
    public void test_completeHabit_dueAt() {
        this.setUp();

        assertThat(habit.dueAt, is(equalTo(null)));

        ////////////////////////////////////////////////////////////////
        // Case 1: The Habit is not required
        // Expected: dueAt should never be set and remain null
        ////////////////////////////////////////////////////////////////
        habit.required = false;
        habit.completeHabit();
        // Assert that it is *still* equal to null.
        assertThat(habit.dueAt, is(equalTo(null)));

        ////////////////////////////////////////////////////////////////
        // Case 2: The Habit is required
        // Expected: dueAt should get updated according to the nextDueAt() method.
        ////////////////////////////////////////////////////////////////
        habit.required = true;
        Date expectedDueAt = Dates.nextDueAt(habit, DateTime.now().toDate());
        habit.completeHabit();
        assertThat(habit.dueAt, is(equalTo(expectedDueAt)));
    }

    @Test
    public void test_completeHabit_streaks() {
        this.setUp();
        // The primary purpose of this test is to ensure that Streaks are calculated
        // correctly when completing a task.

        assertThat(habit.streakValue, is(equalTo(0)));

        // At first this Habit is not set as required, so it shouldn't do anything with the streak.
        // Let's test that it doesn't change when you complete it.
        habit.completeHabit();
        assertThat(habit.streakValue, is(equalTo(0)));

        ////////////////////////////////////////////////////////////////
        // Case 1: The Habit is due today and required.
        // Expected: Completing it now will increase the existing streak.
        ////////////////////////////////////////////////////////////////

        habit.dueAt = DateTime.now().toDate();
        habit.required = true;
        habit.completeHabit();

        // The streak should increase if completed before or on the day it is due.
        assertThat(habit.streakValue, is(equalTo(1)));

        ////////////////////////////////////////////////////////////////
        // Case 2: The Habit has a streak but was due yesterday.
        // Expected: Completing it now will reset the streak to 0 because it's overdue.
        ////////////////////////////////////////////////////////////////

        habit.streakValue = 5;
        habit.dueAt = DateTime.now().minusDays(1).toDate();

        habit.completeHabit();
        // Should have been reset because it was completed late.
        assertThat(habit.streakValue, is(equalTo(0)));
    }

}
