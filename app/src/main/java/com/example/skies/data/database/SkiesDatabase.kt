package com.example.skies.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Database(
    version = 1,
    entities = [Quote_db::class, Task_db::class, Picture_db::class],
)
abstract class SkiesDatabase : RoomDatabase() {

    abstract fun quotesDao(): QuotesDao
    abstract fun taskDao(): TasksDao
    abstract fun pictureDao(): PicturesDao

}