package com.example.skies.data.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "Tasks")
data class Task_db(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("task") val task: String,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("time") val time: String,
    @ColumnInfo("completed") val completed: Boolean
)

@Dao
interface TasksDao{

    @Query("SELECT * FROM Tasks ORDER BY time DESC")
    suspend fun pullAllTasks(): Flow<List<Task_db>>

    @Query("SELECT * FROM Tasks WHERE :title = title")
    suspend fun pullTaskByTitle(title: String): Flow<List<Task_db>>

    @Upsert
    suspend fun upsertTask(task: Task_db)

    @Delete
    suspend fun deleteTask(task: Task_db)

}
