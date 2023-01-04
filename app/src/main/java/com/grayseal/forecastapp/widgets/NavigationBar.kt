package com.grayseal.forecastapp.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NavBar() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = mapOf(
        "Home" to Icons.Filled.Home,
        "Search" to Icons.Filled.Search,
        "Forecast" to Icons.Filled.Analytics,
        "Settings" to Icons.Filled.Settings
    )
    val keys = listOf("Home", "Search", "Forecast", "Settings")

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 15.dp, start = 15.dp, end = 15.dp)
            .clip(
                RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)
            ),
        containerColor = colors.primaryVariant,
    ) {
        keys.forEachIndexed { index, key ->
            NavigationBarItem(
                icon = { items[key]?.let { Icon(it, contentDescription = key) } },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFd68118),
                    unselectedIconColor = Color.White,
                    indicatorColor = colors.primaryVariant
                )
            )
        }
    }
}

