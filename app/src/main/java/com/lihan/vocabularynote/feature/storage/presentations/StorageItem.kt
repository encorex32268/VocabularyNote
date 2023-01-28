package com.lihan.vocabularynote.feature.storage.presentations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

@Composable
fun StorageItem(
    modifier : Modifier = Modifier,
    storage : Storage,
    onClick : (Storage) -> Unit
) {
    val spacer = LocalSpacing.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacer.spaceMedium)
            .clickable {
                onClick(storage)
            }
    ) {
        Box(
          modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(Color.Black)
              .height(80.dp)
        )
        Spacer(modifier = Modifier.width(spacer.spaceMedium))
        Column(
            modifier = Modifier
                .weight(2f)
                .height(80.dp)
            ,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ){
                Text(
                    text = storage.name,
                    style = MaterialTheme.typography.h4
                )
            }
            Spacer(modifier = Modifier.height(spacer.spaceExtraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = storage.description,
                    style = MaterialTheme.typography.body2
                )

            }

        }







    }
}