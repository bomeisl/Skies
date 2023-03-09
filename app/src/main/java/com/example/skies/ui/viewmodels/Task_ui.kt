package com.example.skies.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.skies.data.database.Task_db

data class Task_ui(
    val id: Int = 0,
    val title:String = "",
    val description:String = "",
    val date:String = "",
    val time:String = "",
    val completed:Boolean = false
)

fun Task_ui.toDB(): Task_db = Task_db(
    id = 0,
    title = title,
    task = description,
    date = date,
    time = time,
    completed = completed
)
