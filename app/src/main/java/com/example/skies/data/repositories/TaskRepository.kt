package com.example.skies.data.repositories

import com.example.skies.data.database.TasksDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val tasksDao: TasksDao) {



}