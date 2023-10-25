package com.yx.play.db.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @description
 * @version
 */
@Entity(
    tableName = "yx_qingdan",
)
@Keep
data class QingDanModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var type: String = "",
    var img: String = "",
    var title: String = "",
    var time: String = "",
    var content: String = "",
    var contentType: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is QingDanModel) return false

        if (id != other.id) return false
        if (type != other.type) return false
        if (img != other.img) return false
        if (title != other.title) return false
        if (time != other.time) return false
        if (content != other.content) return false
        if (contentType != other.contentType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + img.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + contentType.hashCode()
        return result
    }
}