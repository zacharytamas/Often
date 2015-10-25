package com.zacharytamas.often.utils;

import com.zacharytamas.often.models.Habit;

import org.joda.time.DateTime;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class Dates {

    public static Boolean getBitForWeekday(Byte mask, int weekday) {
        return (mask & (1 << (weekday - 1))) > 0;
    }

    public static Byte setBitForWeekday(Byte mask, int weekday, Boolean repeat) {
        int weekdayBit = weekday - 1;
        if (repeat) {  // set the bit
            return (byte) (mask | (1 << weekdayBit));
        } else {
            return (byte) (mask & ~(1 << weekdayBit));
        }
    }

    public static boolean isOverdue(Habit habit) {
        return new DateTime(habit.dueAt).isBeforeNow();
    }
}
