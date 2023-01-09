package com.lihan.vocabularynote.presentations.tag

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterBAndW
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.vocabularynote.core.componets.SearchBar
import com.lihan.vocabularynote.core.componets.TitleText
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.domain.model.Tag
import com.lihan.vocabularynote.presentations.tag.components.TagCard

@ExperimentalFoundationApi
@Composable
fun TagScreen(
    onCloseButtonClicked : () -> Unit
) {
    val spacer = LocalSpacing.current
    val cardSizeWidth = LocalConfiguration.current.screenWidthDp.dp / 4
    val cardSizeHeight = LocalConfiguration.current.screenHeightDp.dp / 5

    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            modifier = Modifier
                .size(64.dp)
                .padding(spacer.spaceSmall)
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
            text = Route.TAG
        )
        Spacer(modifier = Modifier.height(spacer.spaceSmall))
        // need fix
        val tags = listOf<Tag>(
            Tag(name = "N", color = Color.Blue.toArgb()),
            Tag(name = "Adj", color = Color.Red.toArgb()),
            Tag(name = "Adv", color = Color.Green.toArgb()),
            Tag(name = "Na Adj", color = Color.Yellow.toArgb()),
            Tag(name = "I Adj", color = MaterialTheme.colors.primary.toArgb()),
            Tag(name = "other", color = MaterialTheme.colors.secondary.toArgb()),
            Tag(name = "like ", color = MaterialTheme.colors.primaryVariant.toArgb()),
        )
        LazyVerticalGrid(cells = GridCells.Fixed(3)){
            items(tags){ item->
                TagCard(
                    modifier = Modifier.size(
                        width = cardSizeWidth,
                        height = cardSizeHeight
                    ),
                    tag = item,
                    onTagClicked = {
                        Log.d("TAG", "TagScreen: ${it.name}")
                    }
                )
            }
        }


    }
}