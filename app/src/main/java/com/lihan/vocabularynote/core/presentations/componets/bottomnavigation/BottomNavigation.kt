package com.lihan.vocabularynote.core.presentations.componets.bottomnavigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(
    modifier : Modifier = Modifier,
    items : List<BottomItem>,
    onItemClicked : (BottomItem) -> Unit = {},
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    androidx.compose.material.BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background
    ) {
        items.forEach {
            BottomNavigationItem(
                selected = it.route == currentDestination?.route?:"",
                onClick = {
                    if (it.route != currentDestination?.route?:""){
                        onItemClicked(it)
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = null,
                    )
                },
                unselectedContentColor = Color.LightGray
            )
        }

    }

}
