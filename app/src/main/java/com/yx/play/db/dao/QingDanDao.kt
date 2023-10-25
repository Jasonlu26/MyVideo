package com.yx.play.db.dao

import androidx.room.*
import com.yx.play.db.model.KaPianModel
import com.yx.play.db.model.QingDanModel
import com.yx.play.db.model.YXHistoryRecordModel
import com.yx.play.db.result.KaPianResult
import com.yx.play.db.result.QingDanResult

/**
 * @description
 * @version
 */
@Dao
interface QingDanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: QingDanModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(models: MutableList<QingDanModel>?)

    @Query("SELECT * FROM yx_qingdan")
    suspend fun getAllQingDan(): MutableList<QingDanModel>?

    @Query("SELECT * FROM yx_qingdan where contentType = (:contentType)")
    suspend fun getTypeQingDan(contentType: String?): MutableList<QingDanModel>?


    @Query("SELECT * FROM yx_qingdan where id = (:id)")
    suspend fun getQingDan(id: Long): QingDanModel?

    @Update(QingDanModel::class)
    suspend fun updateQingDan(result: QingDanResult?)

}