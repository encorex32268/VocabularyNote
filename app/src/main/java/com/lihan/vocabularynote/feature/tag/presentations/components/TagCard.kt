package com.lihan.vocabularynote.feature.tag.presentations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.tag.domain.model.Tag

@Composable
fun TagCard(
    modifier: Modifier = Modifier,
    tag : Tag,
    onTagClicked : (Tag) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(LocalSpacing.current.spaceSmall)
            .clickable {
                onTagClicked(tag)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier
            .weight(10f,true)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color(tag.color))
        )
        Spacer(modifier = modifier.weight(0.5f,true))
        Text(
            modifier = modifier.weight(4.5f,true),
            text = tag.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
    }
    

}