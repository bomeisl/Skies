package com.example.skies.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skies.data.database.Task_db
import com.example.skies.data.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
): ViewModel() {

    val uiStateFlow: MutableStateFlow<List<Task_db>> = MutableStateFlow(listOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.pullAllTasksStream().collect {
                uiStateFlow.value = it
            }
        }
    }
    fun addNewTask(title: String, task: String, date: String, time: String, completed: Boolean) {
        val newTask = Task_db(
            id = 0,
            title = title,
            task = task,
            date = date,
            time = time,
            completed = completed
        )
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.insertTaskInDB(newTask)

            uiStateFlow.value = tasksRepository.pullAllTasks()
        }
    }

    fun editTask(task: Task_db) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.editTaskInDB(task = task)


            uiStateFlow.value  = tasksRepository.pullAllTasks()

        }
    }

    fun onTextChange(task: Task_db) {
        viewModelScope.launch {
            tasksRepository.upsertTaskToDB(task)
            tasksRepository.pullAllTasksStream().collect{
                uiStateFlow.value = it
            }
        }
    }

    fun onDelete(task: Task_db) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteTaskinDB(task)
            uiStateFlow.value = tasksRepository.pullAllTasks()
        }


    }


}