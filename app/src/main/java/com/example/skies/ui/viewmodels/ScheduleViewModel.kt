package com.example.skies.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.skies.data.database.TasksDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val tasksDao: TasksDao
): ViewModel() {

    

}