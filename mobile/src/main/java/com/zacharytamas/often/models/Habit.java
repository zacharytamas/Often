package com.zacharytamas.often.models;

import com.orm.SugarRecord;
import com.zacharytamas.often.utils.Dates;

import java.util.Date;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class Habit extends SugarRecord<Habit> {
    public String title = "";
    public Boolean required = true;
    public int repeatType = 0;
    public int repeatUnit = 0;
    public int repeatScalar = 0;
    public int repeatWeekdays = 0;
    public Date createdAt = new Date();
    public Date availableAt;
    public Date lastCompletedAt;
    public Boolean dueAtSpecificTime = false;
    public Date dueAt;
    public int streakValue = 0;

    public Boolean getRepeatsOnWeekday(int day) {
        return Dates.getBitForWeekday(this.repeatWeekdays, day);
    }

    public void setRepeatsOnWeekday(int day, Boolean repeats) {
        this.repeatWeekdays = Dates.setBitForWeekday(this.repeatWeekdays, day, repeats);
    }

    public void completeTask() {
        this.availableAt = Dates.nextAvailableAt(this, new Date());
    }
}
