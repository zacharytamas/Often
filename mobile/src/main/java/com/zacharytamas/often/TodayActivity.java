package com.zacharytamas.often;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.zacharytamas.often.adapters.TodayAdapter;
import com.zacharytamas.often.models.Habit;
import com.zacharytamas.often.models.managers.HabitManager;
import com.zacharytamas.often.utils.Data;
import com.zacharytamas.often.views.holders.TodayRowViewHolder;

import java.util.List;

/**
 * Created by zacharytamas on 10/25/15.
 */
public class TodayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        this.setTitle(getString(R.string.activity_title_today));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Data.addTestData(this, false);

        HabitManager habitManager = new HabitManager(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Should launch Add Habit Activity", Snackbar.LENGTH_LONG);
            }
        });

        final TodayAdapter todayAdapter = new TodayAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    }
}
