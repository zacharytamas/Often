package com.zacharytamas.often.models.managers

import android.content.Context
import com.zacharytamas.often.models.Habit
import com.zacharytamas.often.utils.Data
import io.realm.Realm
import io.realm.RealmResults
import org.joda.time.DateTime

/**
 * A class for managing queries made for Habit objects.
 * Reminiscent of Django's Managers.
 */
class HabitManager(context: Context) {

    public val mRealm: Realm

    init {
        mRealm = Data.getRealm(context)
    }

    fun getDueHabits() : RealmResults<Habit> {
        return mRealm.where(Habit::class.java)
                     .lessThan("availableAt", DateTime().toDate())
                     .equalTo("required", true)
                     .findAll()
    }

    /**
     * Returns the Habits which are available for completion at the current moment.
     * This excludes Habits whose next occurrence is in the future.
     */
    val availableHabits: RealmResults<Habit>
        get() = mRealm.where(Habit::class.java).lessThan("availableAt", DateTime().toDate()).equalTo("required", false).findAll()

    fun completeHabit(habit: Habit) {
        mRealm.beginTransaction()
//        Habits.completeHabit(habit)
        mRealm.commitTransaction()
    }
}