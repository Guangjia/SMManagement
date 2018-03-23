package com.hxmy.sm.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Created by myron on 3/17/18.
 */

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg model: MessageModel)

    @Query("SELECT * FROM MessageModel where message like :message and time like :time")
    fun findByMessage(message: String?, time: String?): List<MessageModel>
}