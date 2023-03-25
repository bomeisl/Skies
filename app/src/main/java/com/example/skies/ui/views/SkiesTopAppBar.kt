package com.example.skies.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skies.ui.theme.ClassicBlue
import com.example.skies.ui.theme.Sky
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkiesTopAppBar(
    openDrawer: () -> Job,
    title: String
) {
    Column() {


        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Skies",
                        fontFamily = FontFamily.Cursive,
                        color = Color.White,
                        fontSize = 40.sp
                    )
                    Spacer(modifier = Modifier.width(70.dp))
                    Text(
                        text = title,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp
                    )
                }

            },
            modifier = Modifier
                .padding(2.dp),
            navigationIcon = {
                IconButton(
                    onClick = { openDrawer() },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = ClassicBlue),
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                )
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = ClassicBlue,
                scrolledContainerColor = Sky,
                actionIconContentColor = Color.DarkGray,
                navigationIconContentColor = ClassicBlue,
                titleContentColor = Color.White
            )
        )
    }

}
