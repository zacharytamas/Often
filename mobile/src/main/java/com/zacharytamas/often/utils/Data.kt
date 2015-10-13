package com.zacharytamas.often.utils

import android.content.Context
import android.util.Log
import com.zacharytamas.often.models.*
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import java.io.File
import java.util.*

/**
 * Created by zacharytamas on 10/9/15.
 */
object Data {

    fun getRealm(context: Context): Realm {
        val realm: Realm?
        val configuration = RealmConfiguration.Builder(context).build()

        try {
            realm = Realm.getInstance(configuration)
        } catch (e: RealmMigrationNeededException) {
            Realm.migrateRealm(configuration, Migrations())
            // Then get it again.
            realm = Realm.getInstance(configuration)
        }

        // TODO We'll need to check this for Migration issues
        return realm!!
    }

    fun deleteDefaultRealm(context: Context) {
        Realm.deleteRealm(RealmConfiguration.Builder(context).build())
    }

    fun addTestData(context: Context, deleteFirst: Boolean = true) {

        var habitCount: Int

        if (deleteFirst) {
            this.deleteDefaultRealm(context)
            habitCount = 0
        }

        val realm = getRealm(context)
        habitCount = realm.where(Habit::class.java).findAll().size()

        if (habitCount == 0) {
            realm.beginTransaction()

            val habit1 = realm.createObject(Habit::class.java)
            habit1.title = "Brush teeth before bed"
            habit1.repeatType = RepeatType.PERIODICAL
            habit1.repeatUnit = RepeatUnit.DAILY
            habit1.repeatScalar = 1
            habit1.lastCompletedAt = DateTime().minusDays(1).toDate()
            habit1.availableAt = Dates.createDate(2014, 5, 1)
            habit1.dueAt = DateTime().plusDays(1).toDate()
            habit1.required = true
            habit1.streakValue = 20

            val habit2 = realm.createObject(Habit::class.java)
            habit2.title = "Wash face before bed"
            habit2.repeatType = RepeatType.PERIODICAL
            habit2.lastCompletedAt = Dates.createDate(2014, 11, 8)
            habit2.availableAt = Dates.createDate(2014, 9, 1)

            val habit3 = realm.createObject(Habit::class.java)
            habit3.title = "Have car washed"
            habit3.repeatType = RepeatType.PERIODICAL
            habit3.availableAt = Dates.createDate(2014, 10, 25)
            habit3.lastCompletedAt = Dates.createDate(2014, 11, 1)

            val habit4 = realm.createObject(Habit::class.java)
            habit4.title = "Record weight"
            habit4.repeatType = RepeatType.PERIODICAL
            habit4.repeatScalar = 1
            habit4.repeatUnit = RepeatUnit.DAILY
            habit4.availableAt = Dates.createDate(2014, 9, 1)
            habit4.lastCompletedAt = Dates.createDate(2014, 9, 1)
            habit4.dueAt = Dates.createDate(2014, 9, 2)
            habit4.required = true

            val habit5 = realm.createObject(Habit::class.java)
            habit5.title = "Do laundry"
            habit5.repeatType = RepeatType.WEEKLY
            habit5.repeatScalar = 1
            habit5.repeatUnit = RepeatUnit.WEEKLY
            habit5.availableAt = Dates.createDate(2014, 9, 1)
            habit5.lastCompletedAt = Dates.createDate(2014, 11, 1)
            habit5.dueAt = Dates.createDate(2014, 9, 2)
            habit5.required = true

            val habit6 = realm.createObject(Habit::class.java)
            habit6.title = "Take out trash"
            habit6.repeatType = RepeatType.WEEKLY
            habit6.repeatScalar = 1
            habit6.repeatUnit = RepeatUnit.WEEKLY
            habit6.setRepeatOnWeekday(DateTimeConstants.THURSDAY, true)
            habit6.availableAt = Dates.createDate(2014, 9, 1)
            habit6.lastCompletedAt = Dates.createDate(2014, 9, 1)
            habit6.dueAt = Dates.createDate(2014, 9, 2)
            habit6.required = true

            realm.commitTransaction()
        }

    }
    
}