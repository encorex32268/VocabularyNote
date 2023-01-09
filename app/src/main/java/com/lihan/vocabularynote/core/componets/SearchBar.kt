package com.lihan.vocabularynote.core.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.core.ui.LocalSpacing

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange : (String) -> Unit,
    onSearch : ()  -> Unit,
    text : String,
    onFocusChanged : (FocusState) -> Unit,
    shouldShowHint : Boolean = false,
) {
    val spacer = LocalSpacing.current
    Box(
        modifier = modifier
    ){
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                    defaultKeyboardAction(ImeAction.Search)

                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(spacer.spaceExtraTooSmall)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(5.dp)
                )
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()
                .padding(spacer.spaceMedium)
                .padding(end = spacer.spaceMedium)
                .onFocusChanged { onFocusChanged(it) }
        )
        if (shouldShowHint){
            Text(
                text = "Search...",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Light,
                color = Color.LightGray,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = spacer.spaceMedium)
            )
        }
        IconButton(
            onClick = { onSearch() },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }

    }
}