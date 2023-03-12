package com.example.skies.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skies.data.database.Task_db
import com.example.skies.data.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScheduleUiState(
    val taskList: MutableList<Task_db> = mutableListOf<Task_db>(),
)

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
): ViewModel() {
    private val currentTaskID = MutableStateFlow<Int>(0)

    private val _scheduleUiState = MutableStateFlow<List<Task_db>>(mutableListOf())
    val scheduleUiState = _scheduleUiState.asStateFlow()

    private val _taskUiState = MutableStateFlow<Task_db>(Task_db())
    val taskUiState = _taskUiState.asStateFlow()

    fun getTaskList() =
            viewModelScope.launch(Dispatchers.IO) {
                tasksRepository.pullAllTasksStream().collect{
                    _scheduleUiState.value = it
            }
        }

    init {
        getTaskList()
    }

    fun getTask(id: Int) =
            viewModelScope.launch(Dispatchers.IO) {
                _taskUiState.value = tasksRepository.pullTask(id)
            }

    fun initTask(id: Int) {
        currentTaskID.value = id
    }


    fun addNewTask(task: Task_db) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.insertTaskInDB(task)
        }
    }

    fun onTextChange(task: Task_db) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.upsertTaskToDB(task)
        }
    }

    fun onDelete(task: Task_db) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteTaskinDB(task = task)
        }
    }


}