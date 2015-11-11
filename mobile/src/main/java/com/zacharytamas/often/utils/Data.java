package com.zacharytamas.often.utils;

import android.content.Context;

import com.zacharytamas.often.models.Habit;
import com.zacharytamas.often.models.RepeatType;
import com.zacharytamas.often.models.RepeatUnit;

import org.joda.time.DateTime;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class Data {
    public static void addTestData(Context context, boolean deleteFirst) {

        if (deleteFirst) {
            Habit.deleteAll(Habit.class);
        }

        int habitCount = Habit.listAll(Habit.class).size();

        if (habitCount == 0) {
            Habit habit1 = new Habit();
            habit1.title = "Brush teeth before bed";
            habit1.repeatType = RepeatType.PERIODICAL;
            habit1.repeatUnit = RepeatUnit.DAILY;
            habit1.repeatScalar = 1;
            habit1.lastCompletedAt = new DateTime().minusDays(1).toDate();
            habit1.availableAt = new DateTime().minusHours(6).toDate();
            habit1.dueAt = new DateTime().plusDays(1).toDate();
            habit1.required = true;
            habit1.streakValue = 20;
            habit1.save();

            Habit habit2 = new Habit();
            habit2.title = "Wash face before bed";
            habit2.repeatType = RepeatType.PERIODICAL;
            habit2.lastCompletedAt = new DateTime().minusDays(20).toDate();
            habit2.availableAt = new DateTime().minusDays(19).toDate();
            habit2.save();

            Habit habit3 = new Habit();
            habit3.title = "Have car washed";
            habit3.repeatType = RepeatType.PERIODICAL;
            habit3.availableAt = new DateTime().minusDays(24).toDate();
            habit3.lastCompletedAt = new DateTime().minusDays(25).toDate();
            habit3.save();

            Habit habit4 = new Habit();
            habit4.title = "Record weight";
            habit4.repeatType = RepeatType.PERIODICAL;
            habit4.repeatScalar = 1;
            habit4.repeatUnit = RepeatUnit.DAILY;
            habit4.availableAt = new DateTime().minusDays(24).toDate();
            habit4.lastCompletedAt = new DateTime().minusDays(22).toDate();
            habit4.dueAt = new DateTime().minusDays(24).toDate();
            habit4.required = true;
            habit4.save();

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
        }
    }


}
