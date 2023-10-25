package com.yx.play.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yx.play.db.dao.KaPianDao
import com.yx.play.db.dao.QingDanDao
import com.yx.play.db.dao.YXHistoryRecordDao
import com.yx.play.db.model.KaPianModel
import com.yx.play.db.model.QingDanModel
import com.yx.play.db.model.YXHistoryRecordModel


/**
 * @description
 * @version
 */
@Database(
    entities =
    [
        YXHistoryRecordModel::class,
        KaPianModel::class,
        QingDanModel::class
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun historyDataDao(): YXHistoryRecordDao
    abstract fun kapianDao(): KaPianDao
    abstract fun qingdanDao(): QingDanDao
}