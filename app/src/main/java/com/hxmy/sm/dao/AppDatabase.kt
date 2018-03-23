package com.hxmy.sm.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by myron on 3/17/18.
 */
@Database(version = 1, entities = arrayOf(MessageModel::class), exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): MessageDao;
}