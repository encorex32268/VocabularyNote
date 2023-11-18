package com.lihan.vocabularynote.feature.tag.presentations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.tag.domain.model.Tag

@Composable
fun TagCard(
    tag : Tag,
    onTagClicked : (Tag) -> Unit= {},
) {
    Column(
        modifier = Modifier
            .clip(CircleShape)
            .padding(LocalSpacing.current.spaceSmall)
            .clickable {
                onTagClicked(tag)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(80.dp)
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
