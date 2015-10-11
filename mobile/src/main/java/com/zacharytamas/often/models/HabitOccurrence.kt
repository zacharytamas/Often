package com.zacharytamas.often.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Created by zacharytamas on 10/9/15.
 */
@RealmClass
public open class HabitOccurrence : RealmObject() {

    @PrimaryKey open var id: Int = 0
    open var habit: Habit? = null
    open var streakLength: Int = 0
    open var completedAt: Date? = null
    open var wasDueAt: Date? = null

}