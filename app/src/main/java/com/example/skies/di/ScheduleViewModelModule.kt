package com.example.skies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.skies.data.repositories.TasksRepository
import com.example.skies.ui.viewmodels.ScheduleViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object ScheduleViewModelModule {

    @Provides
    fun ProvidesScheduleViewModel(
        tasksRepository: TasksRepository): ScheduleViewModel {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return ScheduleViewModel(
                    tasksRepository
                ) as T
            }
        }
        return ScheduleViewModel(tasksRepository)
    }


}