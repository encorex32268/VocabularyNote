package com.lihan.vocabularynote.feature.storage.presentations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.presentations.componets.TitleText


@Composable
fun StorageItem(
    modifier : Modifier = Modifier,
    storage : Storage,
    onClick : (Storage) -> Unit,
    fontSize : TextUnit = 24.sp
) {
    val spacer = LocalSpacing.current
    var reSizeFontSize by remember{
        mutableStateOf(fontSize)
    }
    var shouldDraw by remember {
        mutableStateOf(false)
    }
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
              .height(80.dp)
        ){
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.icon_folder_storage),
                contentDescription = ""
            )
        }
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
                    fontSize = reSizeFontSize,
                    color = Color.Black,
                    maxLines = 1,
                    onTextLayout = {
                        if (it.didOverflowWidth){
                            reSizeFontSize *= 0.95
                        }else{
                            shouldDraw = true
                        }
                    }
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