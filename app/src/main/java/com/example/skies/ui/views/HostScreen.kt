package com.example.skies.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skies.ui.theme.Sky
import com.example.skies.ui.viewmodels.ScheduleViewModel
import kotlinx.coroutines.launch

enum class Routes {
    HOME,SCHEDULE
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostScreen() {
    val navController: NavHostController = rememberNavController()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier,
        topBar = {
            SkiesTopAppBar{
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        content = {


            NavHost(navController = navController, startDestination = Routes.SCHEDULE.name) {

                    composable(Routes.SCHEDULE.name) {
                        ScheduleScreen(
                            scheduleViewModel = hiltViewModel<ScheduleViewModel>()
                        )
                    }
                }


            },

        bottomBar = {

        },
        snackbarHost = {
            SnackbarHost(hostState = remember { SnackbarHostState() })
        },
        drawerContent = {

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




    )

}


