package com.zacharytamas.often.models

import com.orm.SugarRecord
import java.util.*

/**
 * Created by zacharytamas on 10/9/15.
 */
public class HabitOccurrence : SugarRecord<HabitOccurrence>() {
    
    var habit: Habit? = null
    var streakLength: Int = 0
    var completedAt: Date? = null
    var wasDueAt: Date? = null

}