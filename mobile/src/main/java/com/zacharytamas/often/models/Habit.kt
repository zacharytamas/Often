package com.zacharytamas.often.models

import com.zacharytamas.often.utils.Dates
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Created by zacharytamas on 10/8/15.
 */
@RealmClass
public open class Habit: RealmObject() {
    // TODO Add a unique key back.
//    @PrimaryKey open var id: Int = 0
    open var title: String = ""
    open var required: Boolean = true

    open var repeatType: Byte = 0
    open var repeatUnit: Int = 0
    open var repeatScalar: Int = 0
    open var repeatWeekdays: Byte = 0

    open var createdAt: Date? = null
    open var availableAt: Date? = null
    open var lastCompletedAt: Date? = null
    open var dueAtSpecificTime: Boolean = false
    open var dueAt: Date? = null
    open var streakValue: Int = 0

    @Ignore
    open var repeatOnWeekday: Int = 0

    fun getRepeatOnWeekday(day: Int): Boolean {
        return Dates.getBitForWeekday(this.repeatWeekdays, day);
    }

    fun setRepeatOnWeekday(day: Int, repeats: Boolean) {
        this.repeatWeekdays = Dates.setBitForWeekday(this.repeatWeekdays, day, repeats);
    }
}
