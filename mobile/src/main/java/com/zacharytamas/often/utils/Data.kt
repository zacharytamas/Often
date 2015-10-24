package com.zacharytamas.often.utils

import android.content.Context
import android.util.Log
import com.orm.SugarRecord
import com.zacharytamas.often.models.*
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import java.io.File
import java.util.*

/**
 * Created by zacharytamas on 10/9/15.
 */
object Data {

    fun addTestData(context: Context, deleteFirst: Boolean = true) {

        var habitCount: Int

        if (deleteFirst) {
            SugarRecord.deleteAll(Habit::class.java)
            habitCount = 0
        }

        habitCount = SugarRecord.listAll(Habit::class.java).size;

        if (habitCount == 0) {
            val habit1 = Habit()
            habit1.title = "Brush teeth before bed"
            habit1.repeatType = RepeatType.PERIODICAL
            habit1.repeatUnit = RepeatUnit.DAILY
            habit1.repeatScalar = 1
            habit1.lastCompletedAt = DateTime().minusDays(1).toDate()
            habit1.availableAt = DateTime().minusHours(6).toDate()
            habit1.dueAt = DateTime().plusDays(1).toDate()
            habit1.required = true
            habit1.streakValue = 20
            habit1.save()

            val habit2 = Habit()
            habit2.title = "Wash face before bed"
            habit2.repeatType = RepeatType.PERIODICAL
            habit2.lastCompletedAt = DateTime().minusDays(20).toDate()
            habit2.availableAt = DateTime().minusDays(19).toDate()
            habit2.save()
//
//            val habit3 = realm.createObject(Habit::class.java)
//            habit3.title = "Have car washed"
//            habit3.repeatType = RepeatType.PERIODICAL
//            habit3.availableAt = DateTime().minusDays(24).toDate()
//            habit3.lastCompletedAt = DateTime().minusDays(25).toDate()
//
//            val habit4 = realm.createObject(Habit::class.java)
//            habit4.title = "Record weight"
//            habit4.repeatType = RepeatType.PERIODICAL
//            habit4.repeatScalar = 1
//            habit4.repeatUnit = RepeatUnit.DAILY
//            habit4.availableAt = Dates.createDate(2014, 9, 1)
//            habit4.lastCompletedAt = Dates.createDate(2014, 9, 1)
//            habit4.dueAt = Dates.createDate(2014, 9, 2)
//            habit4.required = true
//
//            val habit5 = realm.createObject(Habit::class.java)
//            habit5.title = "Do laundry"
//            habit5.repeatType = RepeatType.WEEKLY
//            habit5.repeatScalar = 1
//            habit5.repeatUnit = RepeatUnit.WEEKLY
//            habit5.availableAt = Dates.createDate(2014, 9, 1)
//            habit5.lastCompletedAt = Dates.createDate(2014, 11, 1)
//            habit5.dueAt = Dates.createDate(2014, 9, 2)
//            habit5.required = true
//
//            val habit6 = realm.createObject(Habit::class.java)
//            habit6.title = "Take out trash"
//            habit6.repeatType = RepeatType.WEEKLY
//            habit6.repeatScalar = 1
//            habit6.repeatUnit = RepeatUnit.WEEKLY
//            habit6.setRepeatOnWeekday(DateTimeConstants.THURSDAY, true)
//            habit6.availableAt = Dates.createDate(2014, 9, 1)
//            habit6.lastCompletedAt = Dates.createDate(2014, 9, 1)
//            habit6.dueAt = Dates.createDate(2014, 9, 2)
//            habit6.required = true
//
//            realm.commitTransaction()
        }

    }
    
}