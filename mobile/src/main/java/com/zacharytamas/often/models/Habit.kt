package com.zacharytamas.often.models

import io.realm.RealmObject
import java.util.*

/**
 * Created by zacharytamas on 10/8/15.
 */
public class Habit: RealmObject() {
    public var title: String = ""
    public var required: Boolean = true

    public var repeatType: Byte = 0
    public var repeatUnit: Int = 0
    public var repeatScalar: Int = 0
    public var repeatWeekdays: Byte = 0

    public var createdAt: Date? = null
    public var availableAt: Date? = null
    public var lastCompletedAt: Date? = null
    public var dueAtSpecificTime: Boolean = false
    public var dueAt: Date? = null
}
