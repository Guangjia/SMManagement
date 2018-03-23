package com.hxmy.sm

import android.app.Application
import com.hxmy.sm.dao.AppDatabase
import android.arch.persistence.room.Room


/**
 * Created by myron on 3/18/18.
 */
class MyApplication : Application() {

    var db: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "database-myapp").allowMainThreadQueries().build()
    }
}