package com.zacharytamas.often.utils.dates;

import com.zacharytamas.often.models.RepeatType;
import com.zacharytamas.often.models.RepeatUnit;
import com.zacharytamas.often.utils.Dates;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

/**
 * Created by zacharytamas on 10/26/15.
 */
public class NextDueAtTest extends DatesTest {

    @Test
    public void test_nextDueAt() {
        this.setUp();

        habit.repeatUnit = RepeatUnit.DAILY;
        habit.repeatScalar = 1;
        habit.repeatType = RepeatType.PERIODICAL;

        Date nextAvailable = Dates.nextAvailableAt(habit, now);
        Date nextDue = Dates.nextDueAt(habit, now);

        assertSameDay(getDate(1992, 2, 1), nextAvailable);
        // Should be due at midnight of the day, which is 0:00:00 of the next day.
        assertSameDay(getDate(1992, 2, 2), nextDue);

        DateTime dt = new DateTime(nextDue);

        Assert.assertEquals(0, dt.getHourOfDay());
        Assert.assertEquals(0, dt.getMinuteOfHour());
        Assert.assertEquals(0, dt.getSecondOfMinute());
    }
}
