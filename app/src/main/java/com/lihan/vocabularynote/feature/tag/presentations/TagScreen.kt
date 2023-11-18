package com.lihan.vocabularynote.feature.tag.presentations

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.tag.domain.model.Tag
import com.lihan.vocabularynote.feature.tag.presentations.components.TagCard
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme

@ExperimentalFoundationApi
@Composable
fun TagScreen(
    tagState : TagState,
    onCloseButtonClicked : () -> Unit,
    onNavigationNewTag : () -> Unit,
    onNavigationEditTag : (Tag) -> Unit
) {
    val spacer = LocalSpacing.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigationNewTag()
            }){
                Icon(imageVector = Icons.Default.Add, contentDescription ="TagAdd")
            }
        }
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                TitleText(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = com.lihan.vocabularynote.R.string.tag)
                )
            }
            Spacer(modifier = Modifier.height(spacer.spaceSmall))
            LazyVerticalGrid(cells = GridCells.Fixed(4)){
                items(tagState.tags){ item->
                    TagCard(
                        tag = item,
                        onTagClicked = {
                            onNavigationEditTag(it)
                        }
                    )
                }
            }


        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TagScreenPreView(){
    VocabularyNoteTheme {
        TagScreen(
            tagState = TagState(
                listOf(
                    Tag(
                        name = "Test",
                        color = Color.GREEN.toColor().toArgb(),
                        createAt = 12312
                    ),
                    Tag(
                        name = "Test2",
                        color = Color.RED.toColor().toArgb(),
                        createAt = 12312
                    )
                )
            ),
            onCloseButtonClicked = { },
            onNavigationNewTag = { },
            onNavigationEditTag = { tag -> }
        )
    }

}
