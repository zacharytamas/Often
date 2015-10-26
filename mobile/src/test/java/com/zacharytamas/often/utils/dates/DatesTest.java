package com.zacharytamas.often.utils.dates;

import com.zacharytamas.often.models.Habit;

import junit.framework.Assert;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by zacharytamas on 10/26/15.
 */
public class DatesTest {
    protected Date now;
    protected Habit habit;

    protected void setUp() {
        habit = new Habit();
        habit.title = "Brush teeth";
        now = getDate(1992, 1, 31);
    }

    protected void assertSameDay(Date d1, Date d2) {
        Assert.assertEquals(new DateTime(d1).withTimeAtStartOfDay(),
                new DateTime(d2).withTimeAtStartOfDay());
    }

    protected Date getDate(int year, int month, int date) {
        return new DateTime(year, month, date, 0, 0).toDate();
    }
}
