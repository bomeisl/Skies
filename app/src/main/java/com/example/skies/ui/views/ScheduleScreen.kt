package com.example.skies.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.skies.data.database.Task_db
import com.example.skies.ui.viewmodels.ScheduleViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ScheduleScreen(
    scheduleViewModel: ScheduleViewModel = viewModel(),

) {

        ScheduleBody(
            taskList = scheduleViewModel.uiStateFlow,
            addNewTask = {
                    title, task, date, time, completed ->
                scheduleViewModel.addNewTask(
                    title,
                    task,
                    date,
                    time,
                    completed
                )
                         },
            editTask = { task -> scheduleViewModel.editTask(task) },
            onTextChange = { task -> scheduleViewModel.onTextChange(task) },
            onDelete = { task -> scheduleViewModel.onDelete(task) }
        )


}

@Composable
fun TopBanner() {
    Column() {
        Text(text = "Today's Adventure")
    }
}

@Composable
fun ScheduleBody(
    taskList: MutableStateFlow<List<Task_db>>,
    addNewTask: (String, String, String, String, Boolean) -> Unit,
    editTask: (Task_db) -> Unit,
    onTextChange: (Task_db) -> Unit,
    onDelete: (Task_db) -> Unit
) {
    var tasks = taskList.collectAsState()
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)) {

            item{
                BlankTaskCard(
                    addNewTask = addNewTask,
                )
            }

            items(tasks.value) {
                TaskCard(
                    task = it,
                    onTextChange,
                    onDelete
                )
            }
        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    task: Task_db,
    onTextChange: (Task_db) -> Unit,
    onDelete: (Task_db) -> Unit
) {

    val title = remember { mutableStateOf(task.title) }
    val description = remember { mutableStateOf(task.task) }
    val date = remember { mutableStateOf(task.date) }
    val time = remember { mutableStateOf(task.time) }
    val completed = remember { mutableStateOf(task.completed) }

    var entryTask = Task_db(
        id = task.id,
        title = title.value,
        task = description.value,
        date = date.value,
        time = time.value,
        completed = completed.value
    )

    Card(onClick = { /*TODO*/ }) {
        TextField(
            value = title.value,
            onValueChange = {
                newText -> title.value = newText
                onTextChange(entryTask)
            }
        )
        TextField(
            value = description.value,
            onValueChange = {
                newText -> description.value = newText
                onTextChange(entryTask)
            }
        )
        TextField(
            value = date.value,
            onValueChange = {
                newText -> date.value = newText
                onTextChange(entryTask)
            }
        )
        TextField(
            value = time.value,
            onValueChange = {
                newText -> time.value = newText
                onTextChange(entryTask)
            }
        )
        IconButton(onClick = { onDelete(task) }) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlankTaskCard(
    addNewTask: (String, String, String, String, Boolean) -> Unit
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val completed = remember { mutableStateOf(false) }


    Card() {
        TextField(
            value = title.value,
            onValueChange = { newText -> title.value = newText },
            label = { Text(text = "What would you like to do today?") }
        )
        TextField(
            value = description.value,
            onValueChange = { newText -> description.value = newText },
            label = { Text(text = "Details please...") }
        )
        TextField(
            value = date.value,
            onValueChange = { newText -> date.value = newText },
            label = { Text(text = "What time would you like to do that?") }
        )
        TextField(
            value = time.value,
            onValueChange = { newText -> time.value = newText },
            label = { Text(text = "What time would you like to do that?") }
        )
        IconButton(onClick = { addNewTask(title.value, description.value, date.value, time.value, completed.value) }) {
            Icon(imageVector = Icons.Outlined.Send, contentDescription = "")
        }

    }
}

@Composable
fun SkyPic() {
    AsyncImage(
        model = "",
        contentDescription = ""
    )
}