package com.zacharytamas.often.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.pavlospt.CircleView
import com.zacharytamas.often.R
import com.zacharytamas.often.models.Habit
import com.zacharytamas.often.utils.Dates
import org.joda.time.DateTime

/**
 * Created by zacharytamas on 10/24/15.
 */
class TodayAdapter(val context: Context) : RecyclerView.Adapter<TodayAdapter.RowViewHolder>() {

    private var rows: MutableList<Row> = arrayListOf();

    companion object {
        private val HABIT_LAYOUT = R.layout.item_habit
        private val SECTION_HEADER_LAYOUT = R.layout.item_list_header

        private val TYPE_HEADER = 0
        private val TYPE_HABIT = 1
    }

    public fun refill(habits: List<Habit>) {
        this.rows.clear();

        // TODO Go through habits and create lists of Overdue and Available

        this.rows.add(Row(TYPE_HEADER, "Available"));

        for (habit in habits) {
            this.rows.add(Row(TYPE_HABIT, habit))
        }

        notifyDataSetChanged();
    }

    override fun onBindViewHolder(holder: RowViewHolder?, position: Int) {
        holder?.updateView(this.rows.get(position));
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RowViewHolder? {
        var holder: RowViewHolder? = null;
        var view: View? = null;
        val inflater = LayoutInflater.from(parent?.getContext());

        when (viewType) {
            TYPE_HABIT -> {
                view = inflater.inflate(HABIT_LAYOUT, parent, false);
            }
            TYPE_HEADER -> {
                view = inflater.inflate(SECTION_HEADER_LAYOUT, parent, false);
            }
        }

        if (view != null) {
            holder = RowViewHolder(view, viewType, context);
        }

        return holder
    }

    override fun getItemCount(): Int = this.rows.size;
    override fun getItemViewType(position: Int): Int = rows.get(position).type;

    /**
     * Describes a row of the Today Recycler View.
     * This can be a section header type or a Habit type.
     */
    private class Row(val type: Int = 0, val title: String?, val habit: Habit?) {
        constructor(type: Int, title: String) : this(type, title, null);
        constructor(type: Int, habit: Habit) : this(type, null, habit);
    }

    public class RowViewHolder(val view: View, val rowType: Int, val context: Context) : RecyclerView.ViewHolder(view) {

        var mainView: LinearLayout? = null;
        var doneView: RelativeLayout? = null;
        var habitTitle: TextView? = null;
        var lastCompletedTextView: TextView? = null;
        var circleView: CircleView? = null;
        var sectionHeader: TextView? = null;

        init {
            bindView();
        }

        fun updateView(row: Row) {
            when (row.type) {
                TYPE_HEADER -> {
                    sectionHeader!!.text = row.title
                }
                TYPE_HABIT -> {
                    val habit = row.habit!!
                    habitTitle?.text = habit.title
                    if (habit.lastCompletedAt != null) {
                        lastCompletedTextView?.text = view.context.getString(R.string.item_habit_last_completed) +
                                            DateUtils.getRelativeTimeSpanString(habit.lastCompletedAt!!.time,
                                            DateTime().millis,
                                            DateUtils.SECOND_IN_MILLIS).toString()
                    }

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
                        circleView?.setTitleText(Integer.toString(habit.streakValue))
                    } else {
                        circleColor = context.resources.getColor(R.color.greyDark)
                    }

                    circleView?.setFillColor(circleColor)
                }
            }
        }

        fun bindView() {
            when (rowType) {
                TYPE_HEADER -> {
                    sectionHeader = view.findViewById(R.id.sectionTitleTextView) as TextView;
                }
                TYPE_HABIT -> {
                    mainView = view.findViewById(R.id.list_item_main) as LinearLayout;
                    doneView = view.findViewById(R.id.list_item_done_layout) as RelativeLayout;
                    habitTitle = view.findViewById(R.id.habitTitle) as TextView;
                    lastCompletedTextView = view.findViewById(R.id.lastCompletedTextView) as TextView;
                    circleView = view.findViewById(R.id.circleView) as CircleView;
                }
            }
        }

    }

}
