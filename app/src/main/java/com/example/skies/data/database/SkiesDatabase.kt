package com.example.skies.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Database(entities = [Quote_db::class, Task_db::class], version = 1)
abstract class SkiesDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDao
    abstract fun taskDao(): TasksDao

}