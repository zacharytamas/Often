package com.zacharytamas.often.ui.fragments;

import android.graphics.Canvas;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.zacharytamas.often.R;
import com.zacharytamas.often.adapters.HabitListAdapter;
import com.zacharytamas.often.models.managers.HabitManager;
import com.zacharytamas.often.views.holders.HabitRowViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HabitListBaseFragment extends Fragment {

    protected HabitManager habitManager;
    protected HabitListAdapter listAdapter;
    protected RecyclerView recyclerView;

    public HabitListBaseFragment() {
        habitManager = new HabitManager(getActivity());
        listAdapter = new HabitListAdapter(getActivity());
    }

    protected void setupRecyclerView(View view, boolean enableSliding) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);

        if (enableSliding) {
            setupSlideListeners();
        }

    }

    private void setupSlideListeners() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                HabitRowViewHolder holder = (HabitRowViewHolder) viewHolder;
                if (holder.getItemViewType() == HabitListAdapter.TYPE_HEADER) return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                listAdapter.onHabitCompleted(viewHolder.getAdapterPosition());
                viewHolder.itemView.setVisibility(View.GONE);
            }

            private View getDraggableView(RecyclerView.ViewHolder viewHolder) {
                return ((HabitRowViewHolder) viewHolder).getMainView();
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
    }

}
