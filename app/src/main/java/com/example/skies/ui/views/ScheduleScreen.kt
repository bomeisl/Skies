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
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.skies.R
import com.example.skies.data.database.Task_db
import com.example.skies.ui.theme.Immediate
import com.example.skies.ui.theme.LightGreen
import com.example.skies.ui.theme.NotImportant
import com.example.skies.ui.theme.Significant
import com.example.skies.ui.theme.Sky
import com.example.skies.ui.theme.SomewhatImportant
import com.example.skies.ui.theme.TextFieldBackground
import com.example.skies.ui.theme.Urgent
import com.example.skies.ui.viewmodels.ScheduleViewModel
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
    val taskListState = scheduleViewModel.scheduleUiState.collectAsState()
    val snackbarTextColor: MutableState<Color> = remember { mutableStateOf(Sky) }
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
                        {scope.launch {
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
                        onTextChange = { task -> scheduleViewModel.onTextChange(task) },
                        onDelete = { task ->
                            scheduleViewModel.onDelete(task = task)
                        },
                        displaySnackbar = showSnackbar,
                        incrementTaskImportance = { task -> scheduleViewModel.incrementTaskImportance(task = task) }
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

@Composable
fun ScheduleBody(
    taskList: State<List<Task_db>>,
    onAddTask: (Task_db) -> Unit,
    onTextChange: (Task_db) -> Unit,
    onDelete: (Task_db) -> Unit,
    displaySnackbar: (String, Color, String?) -> Job,
    incrementTaskImportance: (Task_db) -> Unit
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .padding(start = 10.dp, top = 0.dp, bottom = 0.dp, end = 10.dp)) {

            item {Spacer(modifier = Modifier.height(90.dp))}

            item{
                BlankExpandableCard(
                    addNewTask = onAddTask,
                    displaySnackbar = displaySnackbar
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
                    onTextChange = onTextChange,
                    displaySnackbar = displaySnackbar,
                    incrementTaskImportance = incrementTaskImportance
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    task: Task_db,
    onTextChange: (Task_db) -> Unit,
    onDelete: (Task_db) -> Unit,
    displaySnackbar: (String, Color, String?) -> Job,
    incrementTaskImportance: (Task_db) -> Unit
) {
    val scope = rememberCoroutineScope()

    val EXPAND_DURATION: Int = 2000
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
            ),

        ) {

        Row() {
            val priorityList: List<Color> = listOf(
            NotImportant, SomewhatImportant, Significant, Urgent, Immediate
        )
            val title = remember { mutableStateOf(task.title) }
            val focusRequester = remember { FocusRequester() }
            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusEvent {
                        if (it.isFocused) {
                            expanded.value = true
                        } else {
                            expanded.value = false
                        }
                    },
                value = title.value,
                onValueChange = {
                    title.value = it
                    onTextChange(Task_db(id = task.id,title=it))
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = TextFieldBackground,
                    unfocusedTextColor = Color.White
                ),
                placeholder = { Text(text = task.title)},
                leadingIcon = {
                    IconButton(
                        onClick = {
                                incrementTaskImportance(task)
                            },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = priorityList[taskImportance.value]),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_task_alt_24),
                                contentDescription = "",
                                tint = priorityList[task.importance-1]
                            )
                        }
                    )
                }
            )


            Row() {

                IconButton(onClick = {
                    onDelete(task)
                        scope.launch(Dispatchers.IO) {

                        }
                    }
                 ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "",
                        tint = iconColor
                    )
                }


                IconButton(onClick = { expanded.value = !expanded.value }) {
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
                onTextChange = {it -> onTextChange(it) }
            )
        }



    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun BlankExpandableCard(
    addNewTask: (Task_db) -> Unit,
    displaySnackbar: (String, Color, String?) -> Job
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


    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = cardBackColor,
            contentColor = cardContentColor,
        ),
        elevation = CardDefaults.cardElevation(cardElevation),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth()


    ) {
        val focusRequester = FocusRequester()
        val titleText = remember { mutableStateOf("") }
            TextField(
                modifier = Modifier
                    .border(width = 0.dp, color = Color.Transparent, shape = RectangleShape)
                    .clipToBounds()
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusEvent {
                        if (it.isFocused) {
                            expanded.value = true
                        } else {
                            expanded.value = false
                        }
                    },
                value = titleText.value,
                onValueChange = {
                    titleText.value = it
                    expanded.value = true
                },
                placeholder = { Text(text = "Schedule an event!") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = TextFieldBackground,
                    unfocusedTextColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.twotone_add_task_24),
                        contentDescription = "",
                        tint = iconColor
                    )
                }
            )
        Row() {
//

            IconButton(onClick = {
                val temp = titleText.value
                expanded.value = false
                titleText.value = ""
                addNewTask(Task_db(title = temp))
                ImeAction.Send
            }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "",
                    tint = iconColor
                )
            }
            
            Spacer(modifier = Modifier.width(300.dp))
            
                IconButton(onClick = { expanded.value = !expanded.value }) {
                    Icon(
                        modifier = Modifier,
                        imageVector = arrowIcon.invoke(),
                        contentDescription = "",
                        tint = iconColor
                    )
                }
            }




        AnimatedVisibility(visible = expanded.value) {
            BlankExpandedContent()
        }
    }

}




@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExpandedContent(
    task: Task_db,
    onTextChange: (Task_db) -> Unit
) {

            Column {
                Divider(Modifier.height(1.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clipToBounds(),
                    label = { Text(text = "Description") },
                    value = "Testing",
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(
                        selectionColors = TextSelectionColors(
                            backgroundColor = Sky,
                            handleColor = Color.Black
                        ),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                )
                )
                Divider(Modifier.height(1.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clipToBounds(),
                    label = { Text(text = "Date") },
                    value = "Test2",
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(
                        selectionColors = TextSelectionColors(
                            backgroundColor = Sky,
                            handleColor = Color.Black
                        ),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                )
                )
                Divider(Modifier.height(1.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clipToBounds(),
                    label = { Text(text = "Time") },
                    value = "Test3",
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(
                        selectionColors = TextSelectionColors(
                            backgroundColor = Sky,
                            handleColor = Color.Black
                        ),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                )
                )

            }
}

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun BlankExpandedContent() {

            Column {
                Divider(Modifier.height(1.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = "Description") },
                    value = "Testing",
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = TextFieldBackground
                    )
                )
                Divider(Modifier.height(1.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = "Date") },
                    value = "Test2",
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = TextFieldBackground
                    )
                )
                Divider(Modifier.height(1.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = "Time") },
                    value = "Test3",
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = TextFieldBackground
                    )
                )
            }
    }



