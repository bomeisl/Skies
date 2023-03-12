package com.example.skies.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                        ScheduleScreen(scaffoldState.snackbarHostState)
                    }
                }


            },
        floatingActionButton = {},

        bottomBar = {
            SkiesBottomNavBar()
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
                    },
                    snackbarHost = {
                        SnackbarHost(hostState = it) {
                            Snackbar(
                                snackbarData = it
                            )
                        }
                    }



    )

}




