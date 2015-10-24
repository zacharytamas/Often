package com.zacharytamas.often.models.managers

import android.content.Context
import com.orm.SugarRecord
import com.zacharytamas.often.models.Habit
import com.zacharytamas.often.utils.Data
import org.joda.time.DateTime

/**
 * A class for managing queries made for Habit objects.
 * Reminiscent of Django's Managers.
 */
class HabitManager(context: Context) {

    /**
     * Returns the Habits which are Available for completion at the current moment
     * and are explicitly due.
     */
//    fun getDueHabits() : RealmResults<Habit> {
//        return mRealm.where(Habit::class.java)
//                     .lessThan("availableAt", DateTime().toDate())
//                     .equalTo("required", true)
//                     .findAll()
//    }

    /**
     * Returns the Habits which are available for completion at the current moment.
     * This excludes Habits whose next occurrence is in the future.
     */
//    fun getAvailableHabits() : RealmResults<Habit> {
//        return mRealm.where(Habit::class.java)
//                     .lessThan("availableAt", DateTime().toDate())
//                     .equalTo("required", false)
//                     .findAll()
//    }

    fun completeHabit(habit: Habit) {
//        mRealm.beginTransaction()
//        Habits.completeHabit(habit)
//        mRealm.commitTransaction()
    }

    fun getAvailableHabits(): List<Habit> {
        // TODO This should actually query for habits that are available
        return SugarRecord.listAll(Habit::class.java)
    }
}