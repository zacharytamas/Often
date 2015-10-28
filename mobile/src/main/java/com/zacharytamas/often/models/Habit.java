package com.zacharytamas.often.models;

import com.orm.SugarRecord;
import com.zacharytamas.often.utils.Dates;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class Habit extends SugarRecord<Habit> {
    public String title = "";
    public Boolean required = false;
    public int repeatType = 0;
    public int repeatUnit = 0;
    public int repeatScalar = 0;
    public int repeatWeekdays = 0;
    public Date createdAt = DateTime.now().toDate();
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

    public void completeHabit() {
        Date now = DateTime.now().toDate();
        this.availableAt = Dates.nextAvailableAt(this, now);
        this.lastCompletedAt = now;

        if (required) {
            // TODO Rethink this. Perhaps the dueAt should be set when its created.
            if (dueAt == null) {
                streakValue = 1;
            // If the Habit is due now or after now, this completion will increase the streakValue.
            } else if (dueAt.after(now) || dueAt.equals(now)) {
                streakValue += 1;
            // If not, they broke the streak, reset to 0.
            } else {
                streakValue = 0;
            }

            dueAt = Dates.nextDueAt(this, now);
        }
    }
}
