package com.yx.play.db.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @description
 * @version
 */
@Entity(
    tableName = "yx_kapian",
)
@Keep
data class KaPianModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var type: String = "",
    var content: String = "",
    var img: String = "",
    var title: String = "",
    var time: String = "",
    var address: String = "",
    var contentType: String = ""
)