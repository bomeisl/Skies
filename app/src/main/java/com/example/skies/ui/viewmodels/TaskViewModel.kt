package com.example.skies.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skies.data.database.Task_db
import com.example.skies.data.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
): ViewModel() {

    fun getTask(id: Int) =
        viewModelScope.async(Dispatchers.Main) {
            viewModelScope.async(Dispatchers.IO) {
                tasksRepository.pullTaskStream(id)
            }.await()
        }



}