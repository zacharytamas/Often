package com.zacharytamas.often.models

import android.test.InstrumentationTestCase
import junit.framework.Assert

import org.joda.time.DateTime
import org.junit.Test
import java.util.*

class HabitTest {

    internal var habit: Habit? = null
    internal var now: Date? = null

    @Throws(Exception::class)
    fun setUp() {

        now = getDate(1992, 1, 31)

        this.habit = Habit()
        val habit = this.habit!!
        habit.availableAt = now
        habit.dueAt = getDate(1992, 1, 1)
    }

    protected fun getDate(year: Int, month: Int, date: Int): Date {
        return DateTime(year, month, date, 0, 0).toDate()
    }

    protected fun assertSameDay(d1: Date, d2: Date) {
        val dateTime1 = DateTime(d1)
        val dateTime2 = DateTime(d2)

        Assert.assertEquals(dateTime1.withTimeAtStartOfDay(),
                dateTime2.withTimeAtStartOfDay())
    }

    @Test
    fun test_setRepeatOnWeekday() {
        this.setUp();
        val habit = this.habit!!
        Assert.assertEquals(habit.repeatWeekdays.toInt(), 0)

        Assert.assertFalse(habit.getRepeatOnWeekday(Calendar.SUNDAY))
        habit.setRepeatOnWeekday(Calendar.SUNDAY, true)
        Assert.assertTrue(habit.getRepeatOnWeekday(Calendar.SUNDAY))
        habit.setRepeatOnWeekday(Calendar.SUNDAY, false)
        Assert.assertFalse(habit.getRepeatOnWeekday(Calendar.SUNDAY))

        Assert.assertFalse(habit.getRepeatOnWeekday(Calendar.MONDAY))
        habit.setRepeatOnWeekday(Calendar.SUNDAY, true)
        habit.setRepeatOnWeekday(Calendar.MONDAY, true)
        Assert.assertTrue(habit.getRepeatOnWeekday(Calendar.SUNDAY))

    }

}
