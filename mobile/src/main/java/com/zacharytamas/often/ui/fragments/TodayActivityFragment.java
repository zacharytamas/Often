package com.zacharytamas.often.ui.fragments;

import android.graphics.Canvas;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zacharytamas.often.R;
import com.zacharytamas.often.adapters.TodayAdapter;
import com.zacharytamas.often.models.Habit;
import com.zacharytamas.often.models.managers.HabitManager;
import com.zacharytamas.often.utils.Data;
import com.zacharytamas.often.views.holders.TodayRowViewHolder;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TodayActivityFragment extends Fragment {

    public TodayActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        Data.addTestData(getActivity(), false);

        HabitManager habitManager = new HabitManager(getActivity());

        final TodayAdapter todayAdapter = new TodayAdapter(getActivity());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(todayAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                TodayRowViewHolder holder = (TodayRowViewHolder) viewHolder;
                if (holder.getItemViewType() == TodayAdapter.TYPE_HEADER) return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                todayAdapter.onHabitCompleted(viewHolder.getAdapterPosition());
                viewHolder.itemView.setVisibility(View.GONE);
            }

            private View getDraggableView(RecyclerView.ViewHolder viewHolder) {
                return ((TodayRowViewHolder) viewHolder).getMainView();
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);

                if (viewHolder.getAdapterPosition() > 0) {
                    viewHolder.itemView.setAlpha(1);
                    getDraggableView(viewHolder).setTranslationX(0);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Fade out the view as it is swiped out of the parent's bounds
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);

                    if (viewHolder.getAdapterPosition() > 0) {
                        getDefaultUIUtil().onDraw(c, recyclerView, getDraggableView(viewHolder), dX, dY, actionState,
                                isCurrentlyActive);
                    }
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        List<Habit> availableHabits = habitManager.getAvailableHabits();

        todayAdapter.refill(availableHabits);

        return view;
    }
}
