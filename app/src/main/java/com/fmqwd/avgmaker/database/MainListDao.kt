package com.fmqwd.avgmaker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * @projectName
 * @author :fmqwd
 * @description:
 * @date :2023/10/29 11:50
 */
@Dao
interface MainListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mainListData: MainListData)

    @Delete
    suspend fun delete(data: MainListData)
    @Update
    fun update(mainListData: MainListData)

    @Query("SELECT * FROM MainListData")
    suspend fun getAll(): List<MainListData>

    @Query("SELECT * FROM MainListData WHERE id = :id")
    suspend fun getById(id: String): MainListData?
}