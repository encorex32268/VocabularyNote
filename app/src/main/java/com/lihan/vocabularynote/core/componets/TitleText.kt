package com.lihan.vocabularynote.core.componets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.core.ui.LocalSpacing

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    text : String,
    fontWeight: FontWeight = FontWeight.ExtraBold,
    fontStyle: FontStyle = FontStyle.Normal,
    fontSize : TextUnit = 64.sp
) {
    Text(
        modifier = modifier.padding(start = LocalSpacing.current.spaceMedium),
        text =text,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontSize = fontSize
    )
}