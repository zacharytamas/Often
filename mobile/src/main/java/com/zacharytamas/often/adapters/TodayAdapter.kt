package com.zacharytamas.often.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by zacharytamas on 10/24/15.
 */
class TodayAdapter : RecyclerView.Adapter<TodayAdapter.HabitAdapterViewHolder>() {
    override fun onBindViewHolder(holder: HabitAdapterViewHolder?, position: Int) {
        throw UnsupportedOperationException()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HabitAdapterViewHolder? {
        throw UnsupportedOperationException()
    }

    override fun getItemCount(): Int {
        throw UnsupportedOperationException()
    }

    public class HabitAdapterViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}