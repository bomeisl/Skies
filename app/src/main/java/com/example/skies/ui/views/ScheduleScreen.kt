package com.example.skies.ui.views

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.skies.R
import com.example.skies.data.database.Task_db
import com.example.skies.ui.theme.ClassicBlue
import com.example.skies.ui.theme.Immediate
import com.example.skies.ui.theme.LightGreen
import com.example.skies.ui.theme.NotImportant
import com.example.skies.ui.theme.Significant
import com.example.skies.ui.theme.Sky
import com.example.skies.ui.theme.SomewhatImportant
import com.example.skies.ui.theme.TextFieldBackground
import com.example.skies.ui.theme.Urgent
import com.example.skies.ui.viewmodels.BlankTaskState
import com.example.skies.ui.viewmodels.ScheduleViewModel
import com.example.skies.ui.viewmodels.TASK
import com.example.skies.ui.viewmodels.toTaskDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter"
)
@Composable
fun ScheduleScreen(
    showSnackbar: (String, Color, String?) -> Job,
    drawerState: DrawerState,
    scheduleViewModel: ScheduleViewModel = hiltViewModel<ScheduleViewModel>()
) {
    val scope = rememberCoroutineScope()
    val taskListState = scheduleViewModel.scheduleUiState.collectAsStateWithLifecycle()
    val snackbarTextColor: MutableState<Color> = remember { mutableStateOf(Sky) }
    val focusRequester = remember { FocusRequester() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column() {
                ModalDrawerSheet() {

                    NavigationDrawerItem(
                        label = { Text(text = "Daily Aspirations", color = Color.Black) },
                        selected = false,
                        onClick = { /*TODO*/ }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Check In", color = Color.Black) },
                        selected = true,
                        onClick = { /*TODO*/ },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Send,
                                contentDescription = ""
                            )
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "My Journal", color = Color.Black) },
                        selected = false,
                        onClick = { /*TODO*/ }
                    )
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    SkiesTopAppBar(
                        {
                            scope.launch {
                            drawerState.open()
                            }
                        },
                        "Daily Aspirations"
                    )
                },
                content = {
                    ScheduleBody(
                        taskList = taskListState,
                        onAddTask = { task -> scheduleViewModel.addNewTask(task) },
                        onDelete = { task ->
                            scheduleViewModel.onDelete(task = task)
                        },
                        displaySnackbar = showSnackbar,
                        incrementTaskImportance = { task -> scheduleViewModel.incrementTaskImportance(task = task) },
                        focusRequester = focusRequester,
                        updateTitle = { title, id -> scheduleViewModel.titleUpdate(title, id) },
                        updateTask = { task, id -> scheduleViewModel.taskUpdate(task, id) },
                        updateDate = { date, id -> scheduleViewModel.dateUpdate(date, id) },
                        updateTime = { time, id -> scheduleViewModel.timeUpdate(time, id) },
                        blankTaskState = scheduleViewModel.blankTaskState,
                        onBlankTextChange = { changeParameter, task -> scheduleViewModel.onBlankTextChange(changeParameter,task) }
                    )
                },
                bottomBar = {
                    SkiesBottomNavBar()
                }
            )
        }
    )


}

@Composable
fun TopBanner() {
    Column() {
        Text(text = "Today's Adventure")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTextField(
    focusRequester: FocusRequester,
    onTextFieldFocus: () -> Unit,
    onLoseTextFieldFocus: () -> Unit,
    task: Task_db,
    incrementTaskImportance: (Task_db) -> Unit = { },
    priorityList: List<Color>? = null,
    taskImportance: MutableState<Int>? = null,
    leadingIcon: Int? = null,
    updateFunction: (String, Int) -> Unit,
    text: String,
    placeholder: String = "",
    label: String = ""
) {

    val holderText = remember { mutableStateOf(text) }

    val scope = rememberCoroutineScope()

    TextField(
        modifier = Modifier,
        value = holderText.value,
        onValueChange = {
            holderText.value = it
            updateFunction(it, task.id)
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = TextFieldBackground,
            unfocusedTextColor = Color.Black,
            focusedTextColor = ClassicBlue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(text = task.title)},
        label = { Text(text = label) },

            leadingIcon = {
                if (leadingIcon != null) {
                    IconButton(
                        onClick = {
                            incrementTaskImportance(task)
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = priorityList!![taskImportance!!.value - 1]
                        ),
                        content = {
                            Icon(
                                painter = painterResource(id = leadingIcon),
                                contentDescription = "",
                                tint = priorityList!![task.importance - 1]
                            )
                        }
                    )
                }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlankScheduleTextField(
    focusRequester: FocusRequester,
    onTextFieldFocus: () -> Unit,
    onLoseTextFieldFocus: () -> Unit,
    leadingIcon: Int? = null,
    iconColor: Color,
    fieldValue: String,
    onBlankTextChange: (TASK, String) -> Unit,
    taskType: TASK,
    placeholder: String = "",
    label: String = ""
) {

    val scope = rememberCoroutineScope()

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = fieldValue,
        onValueChange = {
            onBlankTextChange(taskType, it)
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = TextFieldBackground,
            unfocusedTextColor = Color.Black,
            focusedTextColor = ClassicBlue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(text = placeholder)},
        label = { Text(text = label) },
        leadingIcon = {
            if (leadingIcon != null) {
                IconButton(
                    onClick = {

                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = iconColor
                    ),
                    content = {
                        Icon(
                            painter = painterResource(id = leadingIcon),
                            contentDescription = "",
                            tint = iconColor
                        )
                    }
                )
            }
        }

    )
}

@Composable
fun ScheduleBody(
    taskList: State<List<Task_db>>,
    onAddTask: (Task_db) -> Unit,
    onDelete: (Task_db) -> Unit,
    displaySnackbar: (String, Color, String?) -> Job,
    incrementTaskImportance: (Task_db) -> Unit,
    focusRequester: FocusRequester,
    updateTitle: (String, Int) -> Unit,
    updateTask: (String, Int) -> Unit,
    updateDate: (String, Int) -> Unit,
    updateTime: (String, Int) -> Unit,
    blankTaskState: BlankTaskState,
    onBlankTextChange: (TASK, String) -> Unit
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .padding(start = 10.dp, top = 0.dp, bottom = 0.dp, end = 10.dp)) {

            item {Spacer(modifier = Modifier.height(90.dp))}

            item{
                BlankExpandableCard(
                    addNewTask = onAddTask,
                    displaySnackbar = displaySnackbar,
                    focusRequester = focusRequester,
                    blankTaskState = blankTaskState,
                    onBlankTextChange = onBlankTextChange
                )
                Spacer(modifier = Modifier.height(20.dp))
                }

            items(
                items = taskList.value,
                key = {task ->
                    task.id
                }
            ) { it ->
                ExpandableCard(
                    task = it,
                    onDelete = onDelete,
                    displaySnackbar = displaySnackbar,
                    incrementTaskImportance = incrementTaskImportance,
                    focusRequester = focusRequester,
                    updateTitle = updateTitle,
                    updateTask = updateTask,
                    updateDate = updateDate,
                    updateTime = updateTime
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

        item {Spacer(modifier = Modifier.height(70.dp))}
        }
}

@Composable
fun SkyPic() {
    AsyncImage(
        model = "",
        contentDescription = ""
    )
}


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    task: Task_db,
    onDelete: (Task_db) -> Unit,
    displaySnackbar: (String, Color, String?) -> Job,
    incrementTaskImportance: (Task_db) -> Unit,
    focusRequester: FocusRequester,
    updateTitle: (String, Int) -> Unit,
    updateTask: (String, Int) -> Unit,
    updateDate: (String, Int) -> Unit,
    updateTime: (String, Int) -> Unit
) {

    val title = remember { mutableStateOf(task.title) }
    val description = remember { mutableStateOf(task.task) }
    val date = remember { mutableStateOf(task.date) }
    val time = remember { mutableStateOf(task.time) }

    val scope = rememberCoroutineScope()

    val EXPAND_DURATION: Int = 1500
    val cardExpandedBackgroundColor: Color = Color.White
    val cardCollapsedBackgroundColor: Color = Sky
    val iconExpanded: Color = Sky
    val iconCollapsed: Color = Color.White
    val cardContentColor: Color = Color.Black
    val taskImportance = remember { mutableStateOf(task.importance) }
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

    val iconColor by transition.animateColor(
        { tween(durationMillis = EXPAND_DURATION) }
    ) {
        if (expanded.value) iconExpanded else iconCollapsed
    }

    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_DURATION)
    }) {
        if (expanded.value) 15.dp else 3.dp
    }

    val arrowIcon = {
        if (expanded.value) {
            Icons.Default.KeyboardArrowUp
        } else {
            Icons.Default.KeyboardArrowDown
        }
    }

    val focusManager = LocalFocusManager.current


    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(cardElevation),
        colors = CardDefaults.cardColors(
            containerColor = cardBackColor,
            contentColor = cardContentColor
        ),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = cardElevation,
                clip = true,
                shape = RoundedCornerShape(18.dp)
            )
            .focusRequester(focusRequester)
            .onFocusEvent {
                if (it.isFocused) {
                    expanded.value = true
                } else {
                    expanded.value = false
                }
            }

        ) {

        Row() {
            val priorityList: List<Color> = listOf(
            NotImportant, SomewhatImportant, Significant, Urgent, Immediate
        )

            ScheduleTextField(
                focusRequester = focusRequester,
                onTextFieldFocus =  { expanded.value = true },
                onLoseTextFieldFocus =  { expanded.value = false },
                task = task,
                incrementTaskImportance = incrementTaskImportance,
                priorityList = priorityList,
                taskImportance = taskImportance,
                leadingIcon = R.drawable.baseline_task_alt_24,
                updateFunction = updateTitle,
                text = title.value,
                placeholder = "Type anything here to edit me",
                label = "Task"
            )


            Row() {

                IconButton(onClick = {
                        scope.launch(Dispatchers.IO) {
                            onDelete(task)
                        }
                    }
                 ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "",
                        tint = iconColor
                    )
                }


                IconButton(onClick = {
                    if (expanded.value) {
                        focusManager.clearFocus()
                    }
                    expanded.value = !expanded.value
                }
                ) {
                    Icon(
                        imageVector = arrowIcon.invoke(),
                        contentDescription = "",
                        tint = iconColor
                    )
                }

            }

        }
        
        AnimatedVisibility(visible = expanded.value) {
            ExpandedContent(
                task = task,
                focusRequester = focusRequester,
                onTextFieldFocus = { expanded.value = true },
                onLoseTextFieldFocus = { expanded.value = false },
                updateTask = updateTask,
                updateDate = updateDate,
                updateTime = updateTime,
                description = description,
                date = date,
                time = time
            )
        }



    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun BlankExpandableCard(
    addNewTask: (Task_db) -> Unit,
    displaySnackbar: (String, Color, String?) -> Job,
    focusRequester: FocusRequester,
    blankTaskState: BlankTaskState,
    onBlankTextChange: (TASK, String) -> Unit
) {
    val scope = rememberCoroutineScope()

    val EXPAND_DURATION: Int = 1500
    val cardExpandedBackgroundColor: Color = Color.White
    val cardCollapsedBackgroundColor: Color = LightGreen
    val iconExpanded: Color = LightGreen
    val iconCollapsed: Color = Color.White
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

    val iconColor by transition.animateColor(
        { tween(durationMillis = EXPAND_DURATION) }
    ) {
        if (expanded.value) iconExpanded else iconCollapsed
    }

    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_DURATION)
    }) {
        if (expanded.value) 24.dp else 4.dp
    }

    val arrowIcon = {
        if (expanded.value) {
            Icons.Outlined.KeyboardArrowUp
        } else {
            Icons.Outlined.KeyboardArrowDown
        }
    }

    val focusManager = LocalFocusManager.current

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = cardBackColor,
            contentColor = cardContentColor,
        ),
        elevation = CardDefaults.cardElevation(cardElevation),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusEvent {
                if (it.isFocused) {
                    expanded.value = true
                } else {
                    expanded.value = false
                }
            }

    ) {




            BlankScheduleTextField(
                focusRequester = focusRequester,
                onTextFieldFocus = { expanded.value = true },
                onLoseTextFieldFocus = { expanded.value = false },
                iconColor = LightGreen,
                fieldValue = blankTaskState.title,
                onBlankTextChange = onBlankTextChange,
                taskType = TASK.TITLE,
                placeholder = "What would you like to do today?",
                label = "Task"
            )

            Row() {


                IconButton(onClick = {
                    addNewTask(blankTaskState.toTaskDB())
                    expanded.value = false
                    focusManager.clearFocus()
                }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "",
                        tint = iconColor
                    )
                }

                Spacer(modifier = Modifier.width(300.dp))

                IconButton(onClick = {
                    focusManager.clearFocus()
                    expanded.value = !expanded.value
                }) {
                    Icon(
                        modifier = Modifier,
                        imageVector = arrowIcon.invoke(),
                        contentDescription = "",
                        tint = iconColor
                    )
                }
            }

        AnimatedVisibility(visible = expanded.value) {
            BlankExpandedContent(
                focusRequester = focusRequester,
                expanded = expanded,
                blankTaskState = blankTaskState,
                onBlankTextChange = onBlankTextChange
            )
        }
    }

}




@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExpandedContent(
    task: Task_db,
    focusRequester: FocusRequester,
    onTextFieldFocus: () -> Unit,
    onLoseTextFieldFocus: () -> Unit,
    updateTask: (String, Int) -> Unit,
    updateDate: (String, Int) -> Unit,
    updateTime: (String, Int) -> Unit,
    description: MutableState<String>,
    date: MutableState<String>,
    time: MutableState<String>
) {

            Column {
                Divider(Modifier.height(1.dp))
                ScheduleTextField(
                    focusRequester = focusRequester,
                    onTextFieldFocus = onTextFieldFocus,
                    onLoseTextFieldFocus = onLoseTextFieldFocus,
                    task = task,
                    updateFunction = updateTask,
                    text = description.value,
                    label = "description"
                )

                Divider(Modifier.height(1.dp))
                ScheduleTextField(
                    focusRequester = focusRequester,
                    onTextFieldFocus = onTextFieldFocus,
                    onLoseTextFieldFocus = onLoseTextFieldFocus,
                    task = task,
                    updateFunction = updateDate,
                    text = date.value,
                    label = "date"
                )

                Divider(Modifier.height(1.dp))
                ScheduleTextField(
                    focusRequester = focusRequester,
                    onTextFieldFocus = onTextFieldFocus,
                    onLoseTextFieldFocus = onLoseTextFieldFocus,
                    task = task,
                    updateFunction = updateTime,
                    text = time.value,
                    label = "time"
                )

            }
}

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun BlankExpandedContent(
        focusRequester: FocusRequester,
        expanded: MutableState<Boolean>,
        blankTaskState: BlankTaskState,
        onBlankTextChange: (TASK, String) -> Unit
    ) {

            Column {
                Divider(Modifier.height(1.dp))

                BlankScheduleTextField(
                    focusRequester = focusRequester,
                    onTextFieldFocus = { expanded.value = true },
                    onLoseTextFieldFocus = { expanded.value = false },
                    iconColor = LightGreen,
                    fieldValue = blankTaskState.description,
                    onBlankTextChange = onBlankTextChange,
                    taskType = TASK.DESCRIPTION
                )
                Divider(Modifier.height(1.dp))
                BlankScheduleTextField(
                    focusRequester = focusRequester,
                    onTextFieldFocus = { expanded.value = true },
                    onLoseTextFieldFocus = { expanded.value = false },
                    iconColor = LightGreen,
                    fieldValue = blankTaskState.date,
                    onBlankTextChange = onBlankTextChange,
                    taskType = TASK.DATE
                )
                Divider(Modifier.height(1.dp))
                BlankScheduleTextField(
                    focusRequester = focusRequester,
                    onTextFieldFocus = { expanded.value = true },
                    onLoseTextFieldFocus = { expanded.value = false },
                    iconColor = LightGreen,
                    fieldValue = blankTaskState.time,
                    onBlankTextChange = onBlankTextChange,
                    taskType = TASK.TIME
                )
            }
    }



