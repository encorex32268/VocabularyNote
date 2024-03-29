package com.lihan.vocabularynote.core.presentations.componets.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun VocabularyNoteAppBar(
    title : String = "",
    menuItems: List<MenuItem>,
    onMenuItemClick : (MenuItem) -> Unit,
    onNavigationBack : () -> Unit
) {
    TopAppBar(
        backgroundColor = Color.White,
        title = {
            Text(text = title)
        },
        actions = {
            Row {
                menuItems.forEach {
                    IconButton(
                       onClick = {
                           onMenuItemClick(it)
                       }
                    ){
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.name,
                            tint = Color.Black
                        )
                    }

                }

            }

        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigationBack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription ="Back",
                    tint = Color.Black

                )
            }
        }
    )


}