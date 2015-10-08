package com.zacharytamas.often.utils.dates

import com.zacharytamas.often.models.RepeatType
import com.zacharytamas.often.models.RepeatUnit
import com.zacharytamas.often.utils.Dates;
import org.joda.time.DateTimeConstants
import org.junit.Test
import java.util.*

class NextAvailableAtTest : DatesTest() {

    @Test
    fun test_repeatType_periodically() {
        this.setUp()
        val habit = this.habit!!

        habit.repeatType = RepeatType.PERIODICAL
        habit.repeatScalar = 2

        // Case 1: repeatUnit is daily
        habit.repeatUnit = Calendar.DATE
        assertSameDay(Dates.nextAvailableAt(habit, now!!), getDate(1992, 2, 2))

        // Case 2: repeatUnit is monthly
        habit.repeatUnit = Calendar.MONTH
        assertSameDay(Dates.nextAvailableAt(habit, now!!), getDate(1992, 3, 31))

        // Case 3: repeatUnit is yearly
        habit.repeatUnit = Calendar.YEAR
        assertSameDay(Dates.nextAvailableAt(habit, now!!), getDate(1994, 1, 31))

        // Case 4: repeatUnit is weekly
        habit.repeatUnit = RepeatUnit.WEEKLY
        assertSameDay(Dates.nextAvailableAt(habit, now!!), getDate(1992, 2, 14))

    }

    @Test
    fun test_repeatType_weekly() {
        this.setUp()
        val habit = this.habit!!
        habit.repeatType = RepeatType.WEEKLY
        habit.repeatScalar = 1
        habit.setRepeatOnWeekday(DateTimeConstants.MONDAY, true)
        habit.setRepeatOnWeekday(DateTimeConstants.SATURDAY, true)
        habit.setRepeatOnWeekday(DateTimeConstants.SUNDAY, true)

        // Next should be Saturday
        val next = Dates.nextAvailableAt(habit, now!!)
        assertSameDay(getDate(1992, 2, 1), next)

        // After that is Sunday, the next day.
        val next2 = Dates.nextAvailableAt(habit, next)
        assertSameDay(getDate(1992, 2, 2), next2)

        // After that is Monday, the next day.
        var next3 = Dates.nextAvailableAt(habit, next2)
        assertSameDay(getDate(1992, 2, 3), next3)

        // After that should be the Saturday.
        val next4 = Dates.nextAvailableAt(habit, next3)
        assertSameDay(getDate(1992, 2, 8), next4)

        // Set the repeatScalar to 2 weeks, which should make it skip a week.
        habit.repeatScalar = 2
        next3 = Dates.nextAvailableAt(habit, next)

        // Because the scalar is two weeks, it should skip to the Sunday of
        // the following week instead of the very next one.
        assertSameDay(getDate(1992, 2, 9), next3)

    }

}
