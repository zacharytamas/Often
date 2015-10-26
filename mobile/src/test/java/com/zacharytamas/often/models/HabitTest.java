package com.zacharytamas.often.models;

import com.zacharytamas.often.utils.Dates;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

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
        this.habit.dueAt = new Date();
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
    public void test_completeTask() {
        // The main logic of determining when the next occurrence is scheduled for is
        // covered by the tests for the Dates utils. To avoid testing the same stuff
        // really, I just make sure that the completeTask() method sets that value
        // appropriately and it's what we expect. Not fully exhaustive of selecting
        // all the different repeatTypes, repeatUnits, repeatScalars, etc.

        this.setUp();

        habit.repeatType = RepeatType.PERIODICAL;
        habit.repeatScalar = 2;
        habit.repeatUnit = RepeatUnit.DAILY;

        Date next = Dates.nextAvailableAt(habit, now);
        assertThat(habit.availableAt, is(not(next)));

        habit.completeTask();

        assertThat(habit.availableAt, is(next));

    }

}
