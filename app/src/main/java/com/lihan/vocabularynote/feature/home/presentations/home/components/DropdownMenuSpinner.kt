package com.lihan.vocabularynote.feature.home.presentations.home.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DropdownMenuSpinner(
    isDrop : Boolean = false,
    onDismissRequest : () -> Unit,
    dropMenuItems : List<String> = emptyList(),
    onDropItemSelected : (String)->Unit,

) {
    DropdownMenu(
        expanded = isDrop,
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        dropMenuItems.forEach {
            DropdownMenuItem(
                onClick = {
                    onDropItemSelected(it)
                }
            ) {
                Text(
                    text = it,
                    fontWeight = FontWeight.ExtraLight
                )
            }


        }



    }


}