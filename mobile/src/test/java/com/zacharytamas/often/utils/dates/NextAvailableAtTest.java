package com.zacharytamas.often.utils.dates;

import com.zacharytamas.often.models.RepeatType;
import com.zacharytamas.often.models.RepeatUnit;
import com.zacharytamas.often.utils.Dates;

import org.joda.time.DateTimeConstants;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zacharytamas on 10/26/15.
 */
public class NextAvailableAtTest extends DatesTest {

    @Test
    public void test_repeatType_periodically() {
        this.setUp();

        habit.repeatType = RepeatType.PERIODICAL;
        habit.repeatScalar = 2;

        // Case 1: repeatUnit is daily
        habit.repeatUnit = RepeatUnit.DAILY;
        assertSameDay(Dates.nextAvailableAt(habit, now), getDate(1992, 2, 2));

        // Case 2: repeatUnit is monthly
        habit.repeatUnit = RepeatUnit.MONTHLY;
        assertSameDay(Dates.nextAvailableAt(habit, now), getDate(1992, 3, 31));

        // Case 3: repeatUnit is yearly
        habit.repeatUnit = RepeatUnit.YEARLY;
        assertSameDay(Dates.nextAvailableAt(habit, now), getDate(1994, 1, 31));

        // Case 4: repeatUnit is weekly
        habit.repeatUnit = RepeatUnit.WEEKLY;
        assertSameDay(Dates.nextAvailableAt(habit, now), getDate(1992, 2, 14));
    }

    @Test
    public void test_repeatType_weekly() {
        this.setUp();

        habit.repeatType = RepeatType.WEEKLY;
        habit.repeatScalar = 1;
        habit.setRepeatsOnWeekday(DateTimeConstants.MONDAY, true);
        habit.setRepeatsOnWeekday(DateTimeConstants.SATURDAY, true);
        habit.setRepeatsOnWeekday(DateTimeConstants.SUNDAY, true);

        // Next should be Saturday
        Date next = Dates.nextAvailableAt(habit, now);
        assertSameDay(getDate(1992, 2, 1), next);

        // After that is Sunday, the next day.
        Date next2 = Dates.nextAvailableAt(habit, next);
        assertSameDay(getDate(1992, 2, 2), next2);

        // After that is Monday, the next day.
        Date next3 = Dates.nextAvailableAt(habit, next2);
        assertSameDay(getDate(1992, 2, 3), next3);

        // After that should be the Saturday.
        Date next4 = Dates.nextAvailableAt(habit, next3);
        assertSameDay(getDate(1992, 2, 8), next4);

        // Set the repeatScalar to 2 weeks, which should make it skip a week.
        habit.repeatScalar = 2;
        next3 = Dates.nextAvailableAt(habit, next);

        // Because the scalar is two weeks, it should skip to the Sunday of
        // the following week instead of the very next one.
        assertSameDay(getDate(1992, 2, 9), next3);
    }

}
