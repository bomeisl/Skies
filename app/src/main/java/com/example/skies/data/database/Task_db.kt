package com.example.skies.data.database

import androidx.compose.runtime.mutableStateOf
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.skies.ui.viewmodels.Task_ui
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "Tasks")
data class Task_db(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("title") var title: String  ="",
    @ColumnInfo("task") var task: String = "",
    @ColumnInfo("date") val date: String = "",
    @ColumnInfo("time") val time: String = "",
    @ColumnInfo("completed") var completed: Boolean = false,
    @ColumnInfo("importance") var importance: Int = 1,
    @ColumnInfo("soft_deleted") var soft_deleted: Boolean = false,
    @ColumnInfo(name = "score") var score: Int = 0
)

fun Task_db.toUI(): Task_ui = Task_ui(
    id = id,
    title = title,
    description = task,
    date = date,
    time = time,
    completed = completed,
    score = score
)

@Dao
interface TasksDao{

    @Query("SELECT * FROM Tasks ORDER BY time DESC")
    fun pullAllTasksStream(): Flow<List<Task_db>>

    @Query("SELECT * FROM Tasks ORDER BY time DESC")
    fun pullAllTasks(): List<Task_db>

    @Query("SELECT * FROM Tasks WHERE id = :id")
    fun pullTask(id: Int): Task_db

    @Query("SELECT * FROM Tasks WHERE id = :id")
    fun pullTaskStream(id: Int): Flow<Task_db>

    @Upsert
    suspend fun upsertTask(task: Task_db)

    @Insert
    suspend fun insertTask(task: Task_db)

    @Update
    suspend fun updateTask(task: Task_db)

    @Delete
    suspend fun deleteTask(task: Task_db)

}
