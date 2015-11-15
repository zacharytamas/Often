package com.zacharytamas.often.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zacharytamas.often.R;
import com.zacharytamas.often.models.Habit;
import com.zacharytamas.often.views.holders.HabitRowViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class HabitListAdapter extends RecyclerView.Adapter<HabitRowViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_HABIT = 1;
    private static int HABIT_LAYOUT = R.layout.item_habit;
    private static int SECTION_HEADER_LAYOUT = R.layout.item_list_header;
    private Context mContext;

    private ArrayList<Row> rows = new ArrayList<>();

    public HabitListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public HabitRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = HABIT_LAYOUT;

        if (viewType == TYPE_HEADER) {
            layout = SECTION_HEADER_LAYOUT;
        }

        // TODO Should I check if layout is not null? It shouldn't be possible.
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new HabitRowViewHolder(view, viewType, parent.getContext());
    }

    @Override
    public void onBindViewHolder(HabitRowViewHolder holder, int position) {
        holder.updateView(this.rows.get(position));
    }

    @Override
    public int getItemCount() {
        return this.rows.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.rows.get(position).type;
    }

    public void onHabitCompleted(int itemPosition) {
        Row row = rows.get(itemPosition);
        if (row.type == TYPE_HABIT) {
            Habit habit = row.habit;
            habit.completeHabit();
            habit.save();

            rows.remove(itemPosition);
            notifyItemRemoved(itemPosition);
        }
    }

    public void refill(List<Habit> habits) {
        this.rows.clear();

        // TODO Go through habits and create lists of Overdue and Available

        this.rows.add(new Row(TYPE_HEADER, "Available"));

        for (Habit habit : habits) {
            this.rows.add(new Row(TYPE_HABIT, habit));
        }

        notifyDataSetChanged();
    }

    public class Row {
        public int type;
        public String title;
        public Habit habit;

        public Row(int type, String title) {
            this.type = type;
            this.title = title;
        }

        public Row(int type, Habit habit) {
            this.type = type;
            this.habit = habit;
        }
    }
}
