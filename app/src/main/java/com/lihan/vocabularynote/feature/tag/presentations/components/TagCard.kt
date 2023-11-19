package com.lihan.vocabularynote.feature.tag.presentations.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.tag.domain.model.Tag

@ExperimentalFoundationApi
@Composable
fun TagCard(
    modifier: Modifier = Modifier,
    tag : Tag,
    onTagClicked : (Tag) -> Unit= {},
    onLongClicked : (Tag) -> Unit= {},
) {
    Column(
        modifier = modifier
            .padding(LocalSpacing.current.spaceSmall)
            .combinedClickable(
                onClick = {
                    onTagClicked(tag)
                },
                onLongClick = {
                    onLongClicked(tag)
                }
            )
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(80.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = CircleShape
            )
            .background(
                color = Color(tag.color),
                shape = CircleShape
            )
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.spaceSmall))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = tag.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
    }
    

}

@ExperimentalFoundationApi
@Preview(showSystemUi = true)
@Composable
fun TagCardPreview() {
    TagCard(
        tag = Tag(
            id = 0,
            name = "Test",
            color = Color.Red.toArgb(),
            createAt = 0L
        )
    )
}
