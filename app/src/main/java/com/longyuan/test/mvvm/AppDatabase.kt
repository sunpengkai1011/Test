package com.longyuan.test.mvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        const val DATABASE_NAME = "TEST_DATABASE"

        fun getDatabase(context: Context): AppDatabase?{
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                        .build()
                }
            }
            return INSTANCE
        }
    }
}