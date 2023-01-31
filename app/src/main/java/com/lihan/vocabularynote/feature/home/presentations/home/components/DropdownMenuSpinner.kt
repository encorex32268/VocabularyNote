package com.lihan.vocabularynote.feature.home.presentations.home.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

@Composable
fun DropdownMenuSpinner(
    isDrop : Boolean = false,
    onDismissRequest : () -> Unit,
    dropMenuItems : List<Storage> = emptyList(),
    onDropItemSelected : (Storage)->Unit,

) {
    DropdownMenu(
        expanded = isDrop,
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        DropdownMenuItem(
            onClick = {
                onDropItemSelected(
                    Storage(
                        id = -1,
                        name = "All"
                    ))
            }
        ) {
            Text(
                text = "All",
                fontWeight = FontWeight.ExtraLight
            )
        }
        dropMenuItems.forEach {
            DropdownMenuItem(
                onClick = {
                    onDropItemSelected(it)
                }
            ) {
                Text(
                    text = it.name,
                    fontWeight = FontWeight.ExtraLight
                )
            }
        }



    }


}