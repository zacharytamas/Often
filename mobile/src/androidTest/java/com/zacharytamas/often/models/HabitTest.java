package com.zacharytamas.often.models;

import android.test.InstrumentationTestCase;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.GregorianCalendar;

public class HabitTest extends InstrumentationTestCase {

    Habit habit;
    Date now;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        now = getDate(1992, 1, 31);

        habit = new Habit();
        habit.setAvailableAt(now);
        habit.setDueAt(getDate(1992, 1, 1));
    }

    protected Date getDate(int year, int month, int date) {
        return new DateTime(year, month, date, 0, 0).toDate();
    }

    protected void assertSameDay(Date d1, Date d2) {
        DateTime dateTime1 = new DateTime(d1);
        DateTime dateTime2 = new DateTime(d2);

        assertEquals(dateTime1.withTimeAtStartOfDay(),
                dateTime2.withTimeAtStartOfDay());
    }

    public void test_setRepeatOnWeekday() {

        assertEquals(habit.getRepeatWeekdays(), 0);

        assertFalse(habit.getRepeatsOnWeekday(GregorianCalendar.SUNDAY));
        habit.setRepeatOnWeekday(GregorianCalendar.SUNDAY, true);
        assertTrue(habit.getRepeatsOnWeekday(GregorianCalendar.SUNDAY));
        habit.setRepeatOnWeekday(GregorianCalendar.SUNDAY, false);
        assertFalse(habit.getRepeatsOnWeekday(GregorianCalendar.SUNDAY));

        assertFalse(habit.getRepeatsOnWeekday(GregorianCalendar.MONDAY));
        habit.setRepeatOnWeekday(GregorianCalendar.SUNDAY, true);
        habit.setRepeatOnWeekday(GregorianCalendar.MONDAY, true);
        assertTrue(habit.getRepeatsOnWeekday(GregorianCalendar.SUNDAY));

    }

}
