package com.yx.play.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yx.play.db.model.YXHistoryRecordModel

/**
 * @description
 * @version
 */
@Dao
interface YXHistoryRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(model: YXHistoryRecordModel)

    @Query("SELECT * FROM yx_history where duration > 0 order by timeStamp desc limit 1")
    suspend fun getNewHistory(): YXHistoryRecordModel?

    @Query("SELECT * FROM yx_history where duration > 0 order by timeStamp desc")
    suspend fun getAllHistory(): MutableList<YXHistoryRecordModel>?

    @Query("SELECT * FROM yx_history where tvId = (:vid)")
    suspend fun getHistory(vid: String): YXHistoryRecordModel?

    @Query("delete from yx_history")
    fun deleteAll()
}