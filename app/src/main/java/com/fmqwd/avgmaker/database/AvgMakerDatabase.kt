package com.fmqwd.avgmaker.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @projectName
 * @author :fmqwd
 * @description:
 * @date :2023/10/29 12:00
 */
@Database(entities = [MainListData::class], version = 1)
abstract class AvgMakerDatabase : RoomDatabase() {
    abstract fun mainListDataDao(): MainListDao
}