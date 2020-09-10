package com.hxmy.sm.dao

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by myron on 3/17/18.
 */

@Entity(tableName = "Message95516Model")
class Message95516Model {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
    @ColumnInfo
    var message: String? = null
    @ColumnInfo
    var time: String? = null
}