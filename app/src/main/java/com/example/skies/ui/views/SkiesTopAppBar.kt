package com.example.skies.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skies.ui.theme.ClassicBlue
import com.example.skies.ui.theme.Sky
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkiesTopAppBar(openDrawer: () -> Job) {
    TopAppBar(
        title = {
            Text(
                text = "Skies",
                fontFamily = FontFamily.Cursive,
                color = Color.White,
                fontSize = 40.sp
            ) },
        modifier = Modifier
            .padding(2.dp),
        navigationIcon = {
            IconButton(onClick = { openDrawer() } ){
            Icon(imageVector = Icons.Outlined.Menu, contentDescription = "")
        }
                         },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = ClassicBlue,
            scrolledContainerColor = Sky,
            actionIconContentColor = Color.DarkGray,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White
        )
    )


}