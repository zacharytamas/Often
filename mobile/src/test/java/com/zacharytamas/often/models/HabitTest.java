package com.zacharytamas.often.models;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zacharytamas on 10/26/15.
 */
public class HabitTest {

    Habit habit;
    Date now;

    void setUp() {
        now = getDate(1992, 1, 31);
        this.habit = new Habit();
        this.habit.availableAt = now;
        this.habit.dueAt = getDate(1992, 1, 1);
    }

    private Date getDate(int year, int month, int date) {
        return new DateTime(year, month, date, 0, 0).toDate();
    }

    private void assertSameDay(Date d1, Date d2) {
        Assert.assertEquals(new DateTime(d1).withTimeAtStartOfDay(),
                            new DateTime(d2).withTimeAtStartOfDay());
    }

    @Test public void test_setRepeatOnWeekday() {
        this.setUp();

        Assert.assertEquals((int) habit.repeatWeekdays, 0);

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

}
