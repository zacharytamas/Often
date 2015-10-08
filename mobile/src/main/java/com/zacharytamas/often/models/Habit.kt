package com.zacharytamas.often.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by zacharytamas on 10/8/15.
 */
public class Habit: RealmObject() {
    @PrimaryKey var id: Int = 0
    var title: String = ""
    var required: Boolean = true

    var repeatType: Byte = 0
    var repeatUnit: Int = 0
    var repeatScalar: Int = 0
    var repeatWeekdays: Byte = 0

    var createdAt: Date? = null
    var availableAt: Date? = null
    var lastCompletedAt: Date? = null
    var dueAtSpecificTime: Boolean = false
    var dueAt: Date? = null

    fun getRepeatsOnWeekday(day: Int): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setRepeatOnWeekday(day: Int, repeats: Boolean) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
