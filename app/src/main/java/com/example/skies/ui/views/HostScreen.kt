package com.example.skies.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

enum class Routes {
    HOME,
}

@Composable
fun HostScreen() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME.name ) {



    }
}