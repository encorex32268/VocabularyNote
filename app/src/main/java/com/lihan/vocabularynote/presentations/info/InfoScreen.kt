package com.lihan.vocabularynote.presentations.info

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lihan.vocabularynote.core.componets.TitleText
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing

@Composable
fun InfoScreen(
    onCloseButtonClicked : () -> Unit
) {
    val spacer = LocalSpacing.current
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            modifier = Modifier
                .size(64.dp)
                .padding(spacer.spaceMedium)
                .align(Alignment.End),
            onClick = {
                onCloseButtonClicked()
            }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close"
            )
        }
        TitleText(
            modifier = Modifier.padding(spacer.spaceMedium),
            text = Route.INFO
        )
        Spacer(modifier = Modifier.height(spacer.spaceSmall))
    }
}