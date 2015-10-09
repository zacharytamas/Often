package com.zacharytamas.often.utils

import android.content.Context
import android.util.Log
import io.realm.Realm

/**
 * Created by zacharytamas on 10/9/15.
 */
object Data {

    fun getRealm(context: Context): Realm {
        val realm = Realm.getInstance(context)
        // TODO We'll need to check this for Migration issues
        return realm
    }

    fun addTestData(context: Context, deleteFirst: Boolean = true) {
        val realm = getRealm(context)
        Log.i("Often", "Adding test data.")
    }
    
}