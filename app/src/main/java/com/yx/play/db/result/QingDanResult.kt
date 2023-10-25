package com.yx.play.db.result

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @description
 * @version
 */

@Keep
data class QingDanResult(
    var id: Long = 0,
    var type: String = "",
    var content: String = "",
    var img: String = "",
    var title: String = "",
    var time: String = "",

)