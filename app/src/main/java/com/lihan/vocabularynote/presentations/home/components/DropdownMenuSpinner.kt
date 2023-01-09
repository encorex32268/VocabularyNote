package com.lihan.vocabularynote.presentations.home.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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