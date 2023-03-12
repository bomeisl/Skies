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

enum class Importance {
    ITCANWAIT, IWANTTO, IMPORTANT, URGENT, DOITNOW
}

@Entity(tableName = "Tasks")
data class Task_db(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("title") val title: String  ="",
    @ColumnInfo("task") val task: String = "",
    @ColumnInfo("date") val date: String = "",
    @ColumnInfo("time") val time: String = "",
    @ColumnInfo("completed") val completed: Boolean = false,
    @ColumnInfo("importance") val importance: Importance = Importance.ITCANWAIT,
    @ColumnInfo("soft_deleted") var soft_deleted: Boolean = false
)

fun Task_db.toUI(): Task_ui = Task_ui(
    id = id,
    title = title,
    description = task,
    date = date,
    time = time,
    completed = completed
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
