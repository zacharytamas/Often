package com.zacharytamas.often.utils;

import com.zacharytamas.often.models.Habit;
import com.zacharytamas.often.models.RepeatType;
import com.zacharytamas.often.models.RepeatUnit;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Period;

import java.util.Date;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class Dates {

    public static Boolean getBitForWeekday(int mask, int weekday) {
        return (mask & (1 << (weekday - 1))) > 0;
    }

    public static Byte setBitForWeekday(int mask, int weekday, Boolean repeat) {
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

    public static Date nextAvailableAt(Habit habit, Date now) {

        Date nextAvailableAt = now;
        int repeatScalar = habit.repeatScalar;

        DateTime dt = new DateTime(now);

        switch (habit.repeatType) {
            case RepeatType.PERIODICAL:

                Period period;

                switch (habit.repeatUnit) {
                    case RepeatUnit.DAILY:
                        period = Period.days(repeatScalar);
                        break;
                    case RepeatUnit.MONTHLY:
                        period = Period.months(repeatScalar);
                        break;
                    case RepeatUnit.YEARLY:
                        period = Period.years(repeatScalar);
                        break;
                    case RepeatUnit.WEEKLY:
                        period = Period.weeks(repeatScalar);
                        break;
                    default:
                        return now;
                }

                return dt.plus(period)
                        .withHourOfDay(0)
                        .withMinuteOfHour(0)
                        .withSecondOfMinute(0)
                        .toDate();

            case RepeatType.WEEKLY:
                do {
                    dt = dt.plus(Period.days(1));

                    // If the repeatScalar is more than one and we've just entered
                    // a new week, move ahead.
                    if (repeatScalar > 1 &&
                            dt.getDayOfWeek() == DateTimeConstants.SUNDAY) {
                        dt = dt.plus(Period.days((repeatScalar - 1) * 7));
                    }

                } while (!habit.getRepeatsOnWeekday(dt.getDayOfWeek()));

                return dt.toDate();
        }

        return nextAvailableAt;
    }

    public static Date nextDueAt(Habit habit, Date now) {
        return new DateTime(nextAvailableAt(habit, now))
                .plus(Period.days(1))
                .toDate();
    }
}
