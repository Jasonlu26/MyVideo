package com.yx.play.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yx.play.db.dao.YXHistoryRecordDao
import com.yx.play.db.model.YXHistoryRecordModel


/**
 * @description
 * @version
 */
@Database(
    entities =
    [
        YXHistoryRecordModel::class
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun historyDataDao(): YXHistoryRecordDao
}