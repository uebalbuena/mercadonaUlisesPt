package com.example.mercadonaulisespt.data

import android.content.Context
import androidx.room.Room

object DatabaseClient {

    private var appDatabase: AppDatabase? = null

    fun getAppDatabase(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            ).build()
        }
        return appDatabase!!
    }
}