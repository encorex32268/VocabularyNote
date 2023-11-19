package com.lihan.vocabularynote.core.presentations.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.lihan.vocabularynote.core.ui.LocalSpacing

@Composable
fun VocabularyTextField(
    modifier: Modifier = Modifier,
    text : String,
    onValueChange : (String) -> Unit,
    onDone : () ->Unit = {}

) {
    val spacer = LocalSpacing.current
    BasicTextField(
        textStyle = TextStyle(textAlign = TextAlign.Center),
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        modifier = modifier
            .padding(
                top = spacer.spaceMedium,
                bottom = spacer.spaceMedium
            )
            .clip(RoundedCornerShape(5.dp))
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(spacer.spaceMedium)
            .navigationBarsPadding()
            .imePadding()
    )
}
