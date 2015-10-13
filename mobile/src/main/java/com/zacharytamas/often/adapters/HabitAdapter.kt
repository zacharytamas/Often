package com.zacharytamas.often.adapters

import android.content.Context
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView

import com.github.pavlospt.CircleView
import com.zacharytamas.often.R
import com.zacharytamas.often.models.Habit
import com.zacharytamas.often.utils.Dates

import org.joda.time.DateTime

import java.util.ArrayList

import io.realm.RealmBaseAdapter
import io.realm.RealmResults

/**
 * Created by zjones on 10/13/15.
 */
class TodayAdapter(context: Context, var dueHabits: RealmResults<Habit>, var availableHabits: RealmResults<Habit>) : RealmBaseAdapter<Habit>(context, dueHabits, true), ListAdapter {

    var mRows: ArrayList<Row> = ArrayList()

    private class Row(val type: Int = 0, val title: String, val habit: Habit?) {
        constructor(type: Int, title: String) : this(type, title, null);
        constructor(type: Int, habit: Habit) : this(type, "", habit);
    }

    private class ViewHolder(
            var habitTitle: TextView?,
            var lastCompletedTextView: TextView?,
            var circleView: CircleView?,
            var sectionHeader: TextView?) {
        constructor() : this(null, null, null, null);
    };

    init {
        refill(dueHabits, availableHabits)
    }

    fun refill(dueHabits: RealmResults<Habit>, availableHabits: RealmResults<Habit>) {
        this.dueHabits = dueHabits
        this.availableHabits = availableHabits
        this.mRows = ArrayList<Row>()

        // Build row list
        if (dueHabits.size() > 0) {
            mRows.add(Row(TYPE_HEADER, context.getString(R.string.list_header_due)))
            for (habit in dueHabits) {
                mRows.add(Row(TYPE_HABIT, habit))
            }
        }

        if (availableHabits.size() > 0) {
            mRows.add(Row(TYPE_HEADER, context.getString(R.string.list_header_available)))
            for (habit in availableHabits) {
                mRows.add(Row(TYPE_HABIT, habit))
            }
        }

        notifyDataSetChanged()
    }

    override fun isEnabled(position: Int): Boolean {
        return mRows.get(position).type == TYPE_HABIT
    }

    override fun getViewTypeCount(): Int {
        // Headers and Habits
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return mRows.get(position).type
    }

    override fun getCount(): Int {
        return mRows.size()
    }

    override fun getItem(i: Int): Habit {
        return mRows.get(i).habit!!
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        var viewHolder: ViewHolder? = null

        val row = mRows.get(i)

        if (view == null) {

            when (row.type) {
                TYPE_HEADER -> {
                    view = inflater.inflate(SECTION_HEADER_LAYOUT, viewGroup, false)!!
                    viewHolder = ViewHolder(null, null, null, view?.findViewById(R.id.sectionTitleTextView) as TextView)
                }
                TYPE_HABIT -> {
                    view = inflater.inflate(HABIT_LAYOUT, viewGroup, false)!!
                    viewHolder = ViewHolder(view.findViewById(R.id.habitTitle) as TextView,
                            view.findViewById(R.id.lastCompletedTextView) as TextView,
                            view.findViewById(R.id.circleView) as CircleView,
                            null)
                }
            }

            if (view != null && viewHolder != null) {
                view.tag = viewHolder
            }
        } else {
            viewHolder = view.tag as ViewHolder
        }

        // Tell Kotlin that view should be defined now so we can safely assume that it is.
        view = view!!

        if (row.type == TYPE_HABIT) {
            val habit = mRows.get(i).habit!!
            // NOTE: These ?. are a Kotlin thing. Basically we can't be sure a given viewHolder
            // has all of these things. I *know* they will based on the row type but from a
            // compiler standpoint... it can't know. This probably should hint to me that I've
            // architected something poorly.
            viewHolder?.habitTitle?.text = habit.title

            var convertedDate = ""

            if (habit.lastCompletedAt != null) {
                convertedDate = view.context.getString(R.string.item_habit_last_completed) +
                        DateUtils.getRelativeTimeSpanString(habit.lastCompletedAt!!.time,
                        DateTime().millis,
                        DateUtils.SECOND_IN_MILLIS).toString()
            }

            viewHolder?.lastCompletedTextView?.text = convertedDate

            //
            // Circle
            //

            val circleColor: Int?

            if (habit.required) {
                if (Dates.isOverdue(habit)) {
                    circleColor = context.resources.getColor(R.color.red)
                } else {
                    circleColor = context.resources.getColor(R.color.green)
                }
                viewHolder?.circleView?.setTitleText(Integer.toString(habit.streakValue))
            } else {
                circleColor = context.resources.getColor(R.color.grey)
            }

            viewHolder?.circleView?.setFillColor(circleColor)
        } else if (row.type == TYPE_HEADER) {
            viewHolder?.sectionHeader?.text = row.title
        }

        return view
    }

    companion object {

        private val HABIT_LAYOUT = R.layout.item_habit
        private val SECTION_HEADER_LAYOUT = R.layout.item_list_header

        private val TYPE_HEADER = 0
        private val TYPE_HABIT = 1
    }
}
