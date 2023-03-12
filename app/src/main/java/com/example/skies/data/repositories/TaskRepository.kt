package com.example.skies.data.repositories

import android.content.Context
import com.example.skies.data.database.Task_db
import com.example.skies.data.database.TasksDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class TasksRepository @Inject constructor(private val tasksDao: TasksDao) {



    suspend fun upsertTaskToDB(task: Task_db) {
        tasksDao.upsertTask(task = task)
    }

    suspend fun updateTaskInDB(task: Task_db) {
        tasksDao.updateTask(task = task)
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