package com.example.skies.data.repositories

import android.content.Context
import com.example.skies.data.database.ProductivityDao
import com.example.skies.data.database.Task_db
import com.example.skies.data.database.TasksDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class TasksRepository @Inject constructor(
    private val tasksDao: TasksDao,
    private val productivityDao: ProductivityDao
    ) {

    suspend fun incrementTaskImportance(task: Task_db) {
        tasksDao.updateTask(task = Task_db(
            id = task.id,
            title = task.title,
            date = task.date,
            time = task.time,
            completed = task.completed,
            soft_deleted = task.soft_deleted,
            score = task.score,
            importance  = if (task.importance == 5) 1 else task.importance + 1
        )
        )
    }

    suspend fun onTaskCompletion(task: Task_db) {
        tasksDao.updateTask(task = Task_db(
            id = task.id,
            title = task.title,
            task = task.task,
            date = task.date,
            time = task.time,
            completed = true,
            soft_deleted = task.soft_deleted,
            score = task.score + task.importance,
            importance = task.importance
        )
        )
    }

    suspend fun getTaskProductivity(task: Task_db) {
        productivityDao.getTaskProductivity(task = task.id)
    }

    suspend fun upsertTaskToDB(task: Task_db) {
        tasksDao.upsertTask(task = task)
    }

    suspend fun updateTaskInDB(task: Task_db) {
        tasksDao.updateTask(task = task)
    }

    suspend fun updateTitleInDB(title: String, id: Int) {
        tasksDao.updateTitle(title = title, id = id)
    }

    suspend fun updateTaskInDB(task: String, id: Int) {
        tasksDao.updateTask(task = task, id = id)
    }

    suspend fun updateDateInDB(date: String, id: Int) {
        tasksDao.updateDate(date = date, id = id)
    }

    suspend fun updateTimeInDB(time: String, id: Int) {
        tasksDao.updateTime(time = time, id = id)
    }

    suspend fun insertTaskInDB(task: Task_db) {
        tasksDao.insertTask(task = task)
    }

    suspend fun editTaskInDB(task: Task_db) {
        tasksDao.updateTask(task = task)
    }

    suspend fun deleteTaskinDB(task: Task_db) {
       tasksDao.deleteTask(task = task)
    }

    fun pullAllTasks(): List<Task_db> = tasksDao.pullAllTasks()

    fun pullAllTasksStream(): Flow<List<Task_db>> =
        tasksDao.pullAllTasksStream()


    fun pullTask(id: Int): Task_db = tasksDao.pullTask(id = id)

    fun pullTaskStream(id: Int): Flow<Task_db> = tasksDao.pullTaskStream(id = id)

}