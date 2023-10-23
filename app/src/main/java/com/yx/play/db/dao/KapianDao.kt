package com.yx.play.db.dao

import androidx.room.*
import com.yx.play.db.model.KaPianModel
import com.yx.play.db.model.YXHistoryRecordModel
import com.yx.play.db.result.KaPianResult

/**
 * @description
 * @version
 */
@Dao
interface KaPianDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: KaPianModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(models: MutableList<KaPianModel>?)

    @Query("SELECT * FROM yx_kapian")
    suspend fun getAllKaPian(): MutableList<KaPianModel>?


    @Query("SELECT * FROM yx_kapian where id = (:id)")
    suspend fun getKaPian(id: Long): KaPianModel?

    @Update(KaPianModel::class)
    suspend fun updateKaPian(result: KaPianResult?)

}