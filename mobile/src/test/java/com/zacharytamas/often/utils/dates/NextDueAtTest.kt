package com.zacharytamas.often.utils.dates

import com.zacharytamas.often.models.RepeatType
import com.zacharytamas.often.models.RepeatUnit
import com.zacharytamas.often.utils.Dates
import com.zacharytamas.often.utils.dates.DatesTest
import junit.framework.Assert
import org.joda.time.DateTime
import org.junit.Test

class NextDueAtTest : DatesTest() {

    @Test
    fun test_nextDueAt() {
        this.setUp()
        val habit = this.habit!!
        val now = this.now!!

        habit.repeatUnit = RepeatUnit.DAILY
        habit.repeatScalar = 1
        habit.repeatType = RepeatType.PERIODICAL

        val nextAvailable = Dates.nextAvailableAt(habit, now)
        val nextDue = Dates.nextDueAt(habit, now)

        assertSameDay(getDate(1992, 2, 1), nextAvailable)
        // Should be due at midnight of the day, which is 0:00:00 of the next day.
        assertSameDay(getDate(1992, 2, 2), nextDue)

        val dt = DateTime(nextDue)

        Assert.assertEquals(0, dt.hourOfDay)
        Assert.assertEquals(0, dt.minuteOfHour)
        Assert.assertEquals(0, dt.secondOfMinute)

    }

}