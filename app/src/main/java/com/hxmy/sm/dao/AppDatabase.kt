package com.hxmy.sm.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by myron on 3/17/18.
 */
@Database(version = 1, entities = arrayOf(MessageModel::class, Message95516Model::class, MessageAllModel::class), exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): MessageDao;
    abstract fun msg95516dao(): Message95516Dao;
    abstract fun allDao(): MessageAllDao;
}