package com.zacharytamas.often

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.zacharytamas.often.adapters.TodayAdapter
import com.zacharytamas.often.models.managers.HabitManager
import com.zacharytamas.often.utils.Data

class TodayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today)
        title = getString(R.string.activity_title_today);
        
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        Data.addTestData(this@TodayActivity)

        val habitManager = HabitManager(this);

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Snackbar.make(view, "Should launch Add Habit Activity", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        })

//        val availableHabits = habitManager.getAvailableHabits();
//        val dueHabits = habitManager.getDueHabits();

        val todayAdapter = TodayAdapter();

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        recyclerView.setAdapter(todayAdapter);

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_today, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
