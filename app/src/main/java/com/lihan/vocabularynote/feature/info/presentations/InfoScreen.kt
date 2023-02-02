package com.lihan.vocabularynote.feature.info.presentations

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.BuildConfig
import com.lihan.vocabularynote.core.presentations.componets.TitleText
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
        Spacer(modifier = Modifier.height(spacer.spaceLarge))
    }
}