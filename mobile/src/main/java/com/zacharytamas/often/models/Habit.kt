package com.zacharytamas.often.models

import com.orm.SugarRecord
import com.zacharytamas.often.utils.Dates
import java.util.*

class Habit() : SugarRecord<Habit>() {

    var title: String = ""
    var required: Boolean = true

    var repeatType: Byte = 0
    var repeatUnit: Int = 0
    var repeatScalar: Int = 0
    var repeatWeekdays: Byte = 0

    var createdAt: Date? = Date()
    var availableAt: Date? = null
    var lastCompletedAt: Date? = null
    var dueAtSpecificTime: Boolean = false
    var dueAt: Date? = null
    var streakValue: Int = 0

    fun getRepeatOnWeekday(day: Int): Boolean {
        return Dates.getBitForWeekday(this.repeatWeekdays, day);
    }

    fun setRepeatOnWeekday(day: Int, repeats: Boolean) {
        this.repeatWeekdays = Dates.setBitForWeekday(this.repeatWeekdays, day, repeats);
    }
}
