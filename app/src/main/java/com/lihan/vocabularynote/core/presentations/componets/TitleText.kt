package com.lihan.vocabularynote.core.presentations.componets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
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
    var reSizeFontSize by remember{
        mutableStateOf(fontSize)
    }

    var shouldDraw by remember {
        mutableStateOf(false)
    }
    Text(
        modifier = modifier
            .padding(start = LocalSpacing.current.spaceMedium)
            .drawWithContent {
                    if (shouldDraw){
                        drawContent()
                    }
            }
        ,
        softWrap = false,
        text =text,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontSize = reSizeFontSize,
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