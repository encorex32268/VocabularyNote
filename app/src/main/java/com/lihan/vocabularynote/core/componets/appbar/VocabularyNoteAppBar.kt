package com.lihan.vocabularynote.core.componets.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

@Composable
fun VocabularyNoteAppBar(
    title : String = "",
    menuItems: List<MenuItem>,
    onMenuItemClick : (MenuItem) -> Unit,
    onNavigationBack : () -> Unit
) {
    TopAppBar(
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
                    contentDescription ="Back"
                )
            }
        }
    )


}