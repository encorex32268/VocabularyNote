package com.lihan.vocabularynote.feature.settings.presentations

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing

@Composable
fun SettingsScreen(
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
            text = Route.SETTINGS
        )
        Spacer(modifier = androidx.compose.ui.Modifier.height(spacer.spaceSmall))
    }
}