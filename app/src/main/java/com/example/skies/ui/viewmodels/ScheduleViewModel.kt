package com.example.skies.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skies.data.database.Task_db
import com.example.skies.data.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.concurrent.Task
import javax.inject.Inject

enum class TASK{
    TITLE, DESCRIPTION, DATE, TIME
}
@Stable
interface BlankTaskState {
    val title: String
    val description: String
    val date: String
    val time: String
}

private class MutableBlankTaskState: BlankTaskState {
    override var title by mutableStateOf("")
    override var description by mutableStateOf("")
    override var date by mutableStateOf("")
    override var time by mutableStateOf("")
}

fun BlankTaskState.toTaskDB(): Task_db = Task_db(
    title = title,
    task = description,
    date = date,
    time = time
)

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
): ViewModel() {

    private val _blankTaskState = MutableBlankTaskState()
    val blankTaskState: BlankTaskState = _blankTaskState


    val scheduleUiState = tasksRepository.pullAllTasksStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf<Task_db>()
        )

    fun addNewTask(task: Task_db) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.insertTaskInDB(task)
        }
        _blankTaskState.title = ""
        _blankTaskState.description = ""
        _blankTaskState.date = ""
        _blankTaskState.time = ""
    }

    fun titleUpdate(title: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateTitleInDB(title, id)
        }
    }

    fun taskUpdate(task: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateTaskInDB(task, id)
        }
    }

    fun dateUpdate(date: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateDateInDB(date, id)
        }
    }

    fun timeUpdate(time: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.updateTimeInDB(time, id)
        }
    }

    fun onDelete(task: Task_db) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.deleteTaskinDB(task = task)
        }
    }

    fun incrementTaskImportance(task: Task_db) {
        viewModelScope.launch {
            tasksRepository.incrementTaskImportance(task)
        }
    }

    fun onBlankTextChange(changeParameter: TASK, text: String) {
        when (changeParameter) {
            TASK.TITLE -> _blankTaskState.title = text
            TASK.DESCRIPTION -> _blankTaskState.description = text
            TASK.DATE -> _blankTaskState.date = text
            TASK.TIME -> _blankTaskState.time = text
        }
    }


}