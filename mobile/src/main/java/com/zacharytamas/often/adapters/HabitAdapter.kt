package com.zacharytamas.often.adapters

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.RelativeLayout
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

    companion object {
        private val HABIT_LAYOUT = R.layout.item_habit
        private val SECTION_HEADER_LAYOUT = R.layout.item_list_header

        private val TYPE_HEADER = 0
        private val TYPE_HABIT = 1
    }

    private class Row(val type: Int = 0, val title: String, val habit: Habit?) {
        constructor(type: Int, title: String) : this(type, title, null);
        constructor(type: Int, habit: Habit) : this(type, "", habit);
    }

    private class ViewHolder(
            var mainView: LinearLayout?,
            var doneView: RelativeLayout?,
            var habitTitle: TextView?,
            var lastCompletedTextView: TextView?,
            var circleView: CircleView?,
            var sectionHeader: TextView?) {
        constructor() : this(null, null, null, null, null, null);
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

    override fun getViewTypeCount(): Int = 2

    override fun getItemViewType(position: Int): Int = mRows.get(position).type

    override fun getCount(): Int = mRows.size()

    override fun getItem(i: Int): Habit = mRows.get(i).habit!!

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        var viewHolder: ViewHolder? = null

        val row = mRows.get(i)

        if (view == null) {

            when (row.type) {
                TYPE_HEADER -> {
                    view = inflater.inflate(SECTION_HEADER_LAYOUT, viewGroup, false)!!
                    viewHolder = ViewHolder(null, null, null, null, null,
                            view.findViewById(R.id.sectionTitleTextView) as TextView)
                }
                TYPE_HABIT -> {
                    view = inflater.inflate(HABIT_LAYOUT, viewGroup, false)!!
                    viewHolder = ViewHolder(view.findViewById(R.id.list_item_main) as LinearLayout,
                            view.findViewById(R.id.list_item_done_layout) as RelativeLayout,
                            view.findViewById(R.id.habitTitle) as TextView,
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
        viewHolder = viewHolder!!

        if (row.type == TYPE_HABIT) {
            val habit = mRows.get(i).habit!!
            viewHolder.habitTitle?.text = habit.title

            // TODO I wonder if there is a way I can do this at a higher level so I have one
            // SwipeDetector instead of one per row. Similar to using delegated listeners
            // on the web.
            view.setOnTouchListener(SwipeDetector(viewHolder, i));

            var convertedDate = ""

            if (habit.lastCompletedAt != null) {
                convertedDate = view.context.getString(R.string.item_habit_last_completed) +
                        DateUtils.getRelativeTimeSpanString(habit.lastCompletedAt!!.time,
                        DateTime().millis,
                        DateUtils.SECOND_IN_MILLIS).toString()
            }

            viewHolder.lastCompletedTextView?.text = convertedDate

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
                viewHolder.circleView?.setTitleText(Integer.toString(habit.streakValue))
            } else {
                circleColor = context.resources.getColor(R.color.grey)
            }

            viewHolder.circleView?.setFillColor(circleColor)
        } else if (row.type == TYPE_HEADER) {
            viewHolder.sectionHeader?.text = row.title
        }

        return view
    }

    class SwipeDetector(val viewHolder: ViewHolder, position: Int) : View.OnTouchListener {

        var downX: Float = 0F;
        var upX: Float = 0F;

        companion object {
            val MIN_DISTANCE = 300;
            val MIN_LOCK_DISTANCE = 30;
        }

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            var event = event!!
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.getX();
                    return true;
                }

                MotionEvent.ACTION_MOVE -> {
                    upX = event.getX();
                    val deltaX = downX - upX;

                    if (Math.abs(deltaX) > MIN_LOCK_DISTANCE) {
                        swipe(-deltaX);
                    }

//                    if (deltaX > 0) {
//                        viewHolder.doneView?.visibility = View.GONE;
//                    } else {
//                        viewHolder.doneView?.visibility = View.VISIBLE;
//                    }

                    return true;
                }

                MotionEvent.ACTION_UP -> {
                    upX = event.getX()
                    val deltaX = upX - downX;

                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        swipeDone();
                    } else if(Math.abs(deltaX) > MIN_LOCK_DISTANCE) {
                        swipe(0F);
                    }

//                    viewHolder.doneView?.visibility = View.VISIBLE
                    return true
                }

                MotionEvent.ACTION_CANCEL -> {
                    swipe(0F)
//                    viewHolder.doneView?.visibility = View.GONE
                }
            }

            return true
        }

        private fun swipeDone() {
            Log.i("HabitAdapter", "should mark done")
        }

        private fun swipe(distance: Float) {
            val animationView = viewHolder.mainView!!
            animationView.setX(distance)
        }
    }
}
