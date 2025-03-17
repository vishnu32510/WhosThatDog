package com.vn.whosthatdog.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NavigationBottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "search",
                )
            },
            alwaysShowLabel = false,
            label = {
                Text(text = "Home")
            },
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "search",
                )
            },
            alwaysShowLabel = false,
            label = {
                Text(text = "Person")
            },
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "search",
                )
            },
            alwaysShowLabel = false,
            label = {
                Text(text = "Setting")
            },
        )
    }
}