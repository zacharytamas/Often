package com.zacharytamas.often.views.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.pavlospt.CircleView;
import com.zacharytamas.often.R;
import com.zacharytamas.often.adapters.HabitListAdapter;
import com.zacharytamas.often.models.Habit;
import com.zacharytamas.often.utils.Dates;

import org.joda.time.DateTime;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class HabitRowViewHolder extends RecyclerView.ViewHolder {

    private final Context mContext;
    private int mRowType;
    private LinearLayout mMainView;
    private RelativeLayout mDoneView;
    private TextView mHabitTitle;
    private TextView mLastCompletedTextView;
    private CircleView mCircleView;
    private TextView mSectionHeader;

    public HabitRowViewHolder(View itemView, int rowType, Context context) {
        super(itemView);
        mRowType = rowType;
        mContext = context;
        bindView();
    }

    private void bindView() {
        switch (mRowType) {
            case HabitListAdapter.TYPE_HEADER:
                mSectionHeader = (TextView) itemView.findViewById(R.id.sectionTitleTextView);
                break;
            case HabitListAdapter.TYPE_HABIT:
                mMainView = (LinearLayout) itemView.findViewById(R.id.list_item_main);
                mDoneView = (RelativeLayout) itemView.findViewById(R.id.list_item_done_layout);
                mHabitTitle = (TextView) itemView.findViewById(R.id.habitTitle);
                mLastCompletedTextView = (TextView) itemView.findViewById(R.id.lastCompletedTextView);
                mCircleView = (CircleView) itemView.findViewById(R.id.circleView);
                break;
        }
    }

    public LinearLayout getMainView() {
        return mMainView;
    }

    public void updateView(HabitListAdapter.Row row) {
        switch (row.type) {
            case HabitListAdapter.TYPE_HEADER:
                mSectionHeader.setText(row.title);
                break;
            case HabitListAdapter.TYPE_HABIT:
                Habit habit = row.habit;
                mMainView.setTranslationX(0);
                mHabitTitle.setText(habit.title);

                if (habit.lastCompletedAt != null) {
                    mLastCompletedTextView.setText(String.format("%s%s", mContext.getString(R.string.item_habit_last_completed),
                            DateUtils.getRelativeTimeSpanString(habit.lastCompletedAt.getTime(),
                            new DateTime().getMillis(), DateUtils.SECOND_IN_MILLIS)));
                }

                //
                // Circle
                //

                int circleColor = mContext.getResources().getColor(R.color.greyDark);

                if (habit.required) {
                    if (Dates.isOverdue(habit)) {
                        circleColor = mContext.getResources().getColor(R.color.red);
                    } else {
                        circleColor = mContext.getResources().getColor(R.color.green);
                    }
                }

                mCircleView.setFillColor(circleColor);

                break;
        }
    }
}
