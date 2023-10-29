package com.fmqwd.avgmaker.database

import android.content.Context
import androidx.room.Room

/**
 * @projectName
 * @author :fmqwd
 * @description:
 * @date :2023/10/29 12:03
 */
object DatabaseManager {
    private var database: AvgMakerDatabase? = null

    fun initialize(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AvgMakerDatabase::class.java,
                "AvgMaker"
            ).build()
        }
    }

    fun getDatabase(): AvgMakerDatabase {
        return database ?: throw IllegalStateException("Database not initialized")
    }
}