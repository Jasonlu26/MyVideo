package com.yx.play.db.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @description
 * @version
 */
@Entity(
    tableName = "yx_history",
)
@Keep
data class YXHistoryRecordModel(
    @PrimaryKey
    var tvId: String = "",
    var name : String = "",
    var playUrl : String = "",
    var imageUrl : String = "",
    var duration: Long = 0L,
    var playDuration: Long = 0L,
    var playName: String = "",
    var timeStamp: Long = 0L
)