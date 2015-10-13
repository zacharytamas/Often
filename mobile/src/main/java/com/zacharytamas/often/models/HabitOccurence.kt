package com.zacharytamas.often.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by zacharytamas on 10/9/15.
 */
class HabitOccurence : RealmObject() {

    @PrimaryKey var id: Int = 0
    var habit: Habit? = null
    var streakLength: Int = 0
    var completedAt: Date? = null
    var wasDueAt: Date? = null

}