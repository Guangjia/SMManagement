package com.hxmy.sm

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.hxmy.sm.dao.AppDatabase


/**
 * Created by myron on 3/18/18.
 */
class MyApplication : Application() {

    var db: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "database-myapp").allowMainThreadQueries().build()

        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
    }

    var sharedPreferences: SharedPreferences? = null;

    fun getSharePReference(): SharedPreferences? {
        return sharedPreferences;
    }
}