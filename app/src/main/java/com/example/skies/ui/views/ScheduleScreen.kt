package com.example.skies.ui.views

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.skies.data.database.Task_db
import com.example.skies.ui.theme.FadedSky
import com.example.skies.ui.theme.Sky
import com.example.skies.ui.viewmodels.ScheduleViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ScheduleScreen(scheduleViewModel: ScheduleViewModel) {

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
            onDelete = {
                    task -> scheduleViewModel.onDelete(task)
            }
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
                ExpandableCard(
                    task = it,
                    onTextChange = onTextChange,
                    onDelete = onDelete,
                    onClick = { /*TODO*/ },
                    expanded = false
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
        IconButton(onClick = {
            onDelete(task)
        }) {
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

@Composable
fun ScheduleSnackbar() {
    Snackbar(
        content = {  },
        
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    task: Task_db,
    onTextChange: (Task_db) -> Unit,
    onDelete: (Task_db) -> Unit,
    onClick: () -> Unit,
    expanded: Boolean

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

    val EXPAND_DURATION: Int = 2000
    val cardExpandedBackgroundColor: Color = FadedSky
    val cardCollapsedBackgroundColor: Color = Sky
    val cardContentColor: Color = Color.Black
    val expanded = remember { mutableStateOf(false) }

    val transitionState = remember {
        MutableTransitionState(expanded.value).apply {
            targetState = !expanded.value
        }
    }

    val transition = updateTransition(transitionState)
    val cardBackColor by transition.animateColor(
        { tween(durationMillis = EXPAND_DURATION) }
    ) {
            if (expanded.value) cardExpandedBackgroundColor else cardCollapsedBackgroundColor
    }

    val cardPaddingHorizontal by transition.animateDp(
        { tween(durationMillis = EXPAND_DURATION) }
    ) {
        if (expanded.value) 48.dp else 24.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_DURATION)
    }) {
        if (expanded.value) 24.dp else 4.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = EXPAND_DURATION,
            easing = FastOutSlowInEasing
        )
    }) {
        if (expanded.value) 0.dp else 16.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPAND_DURATION)
    }) {
        if (expanded.value) 0f else 180f
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardBackColor,
            contentColor = cardContentColor
        ),
        elevation = CardDefaults.cardElevation(cardElevation),
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) {
        Row() {
            Box() {
                TextField(
                    value = title.value,
                    onValueChange = {
                            newText -> title.value = newText
                        onTextChange(entryTask)
                    }
                )
            }
            
            IconButton(onClick = { expanded.value = !expanded.value }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowDropDown,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
        

        ExpandedContent(
            visibility = expanded,
            task = task,
            onTextChange = onTextChange,
            onDelete = onDelete,
            onClick = onClick
        )

    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandedContent(
    visibility: MutableState<Boolean>,
    task: Task_db,
    onTextChange: (Task_db) -> Unit,
    onDelete: (Task_db) -> Unit,
    onClick: () -> Unit,
) {
    val visible = MutableTransitionState(visibility.value)
    val EXPANSION_DURATION: Int = 2000
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSION_DURATION)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANSION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSION_DURATION)
        ) + fadeOut(
            // Fade in with the initial alpha of 0.3f.
            animationSpec = tween(EXPANSION_DURATION)
        )
    }
    AnimatedVisibility(
        visibleState = visible,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        Column() {
            TextField(
                value = "Testing",
                onValueChange = {}
            )
            TextField(
                value = "Test2",
                onValueChange = {}
            )
            TextField(
                value = "Test3",
                onValueChange = {}
            )
        }
    }

}