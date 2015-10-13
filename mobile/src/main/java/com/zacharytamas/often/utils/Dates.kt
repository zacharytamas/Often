package com.zacharytamas.often.utils

import com.zacharytamas.often.models.Habit
import com.zacharytamas.often.models.RepeatType
import com.zacharytamas.often.models.RepeatUnit

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.Period
import java.util.*


/**
 * Created by zacharytamas on 12/12/14.
 */
object Dates {

    fun setBitForWeekday(mask: Byte, weekday: Int, repeat: Boolean): Byte {

        val weekdayBit = weekday - 1

        if (repeat) {
            // set the bit
            return (mask.toInt() or (1 shl weekdayBit)).toByte()
        } else {
            // clear the bit
            return (mask.toInt() and (1 shl weekdayBit).inv()).toByte()
        }
    }

    fun getBitForWeekday(mask: Byte, weekday: Int): Boolean {
        return (mask.toInt() and (1 shl (weekday - 1))) > 0
    }

    fun nextAvailableAt(habit: Habit, now: Date): Date {

        val nextAvailableAt = now
        val repeatScalar = habit.repeatScalar

        var dt = DateTime(now)

        when (habit.repeatType) {
            RepeatType.PERIODICAL -> {

                val period: Period

                when (habit.repeatUnit) {
                    Calendar.DATE -> period = Period.days(repeatScalar)
                    Calendar.MONTH -> period = Period.months(repeatScalar)
                    Calendar.YEAR -> period = Period.years(repeatScalar)
                    RepeatUnit.WEEKLY -> period = Period.weeks(repeatScalar)
                    else -> return now
                }

                return dt.plus(period).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate()
            }

            RepeatType.WEEKLY -> {
                do {
                    dt = dt.plus(Period.days(1))

                    // If the repeatScalar is more than one and we've just entered
                    // a new week, move ahead.
                    if (repeatScalar > 1 && dt.dayOfWeek == DateTimeConstants.SUNDAY) {
                        dt = dt.plus(Period.days((repeatScalar - 1) * 7))
                    }

                } while (!habit.getRepeatOnWeekday(dt.dayOfWeek))

                return dt.toDate()
            }
        }

        return nextAvailableAt
    }

    fun nextDueAt(habit: Habit, now: Date): Date {
        return DateTime(nextAvailableAt(habit, now)).plus(Period.days(1)).toDate()
    }
    
    fun isOverdue(habit: Habit): Boolean {
        return DateTime(habit.dueAt).isBeforeNow
    }

    fun createDate(year: Int, month: Int, day: Int): Date {
        val cal = Calendar.getInstance()
        cal.timeInMillis = 0
        cal.set(year, month, day, 17, 0, 0)
        return cal.time
    }
}
