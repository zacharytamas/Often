package com.zacharytamas.often.utils

import android.content.Context
import android.util.Log
import com.zacharytamas.often.models.*
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmMigrationNeededException
import org.joda.time.DateTime
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

    fun addTestData(context: Context, deleteFirst: Boolean = true) {

        var habitCount: Int

        if (deleteFirst) {
            Realm.deleteRealm(RealmConfiguration.Builder(context).build())
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

            realm.commitTransaction()
        }

    }
    
}