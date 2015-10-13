package com.zacharytamas.often.utils.dates

import com.zacharytamas.often.models.Habit
import junit.framework.Assert
import org.joda.time.DateTime
import java.util.*

/**
 * Created by zacharytamas on 10/8/15.
 */
open class DatesTest {

    var now: Date? = null
    var habit: Habit? = null

    protected fun setUp() {
        habit = Habit()
        habit?.title = "Brush teeth"
        now = getDate(1992, 1, 31)
    }

    protected fun assertSameDay(d1: Date, d2: Date) {
        val dateTime1 = DateTime(d1)
        val dateTime2 = DateTime(d2)

        Assert.assertEquals(dateTime1.withTimeAtStartOfDay(),
                dateTime2.withTimeAtStartOfDay())
    }

    protected fun getDate(year: Int, month: Int, date: Int): Date {
        return DateTime(year, month, date, 0, 0).toDate()
    }

}