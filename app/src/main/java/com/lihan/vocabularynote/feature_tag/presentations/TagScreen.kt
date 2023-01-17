package com.lihan.vocabularynote.feature_tag.presentations

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature_tag.domain.model.Tag
import com.lihan.vocabularynote.feature_tag.presentations.components.TagCard

@ExperimentalFoundationApi
@Composable
fun TagScreen(
    onCloseButtonClicked : () -> Unit,
    viewModel: TagViewModel = hiltViewModel(),
    onNavigationNewTag : () -> Unit,
    onNavigationEditTag : (Tag) -> Unit
) {
    val spacer = LocalSpacing.current
    val cardSizeWidth = LocalConfiguration.current.screenWidthDp.dp / 4
    val cardSizeHeight = LocalConfiguration.current.screenHeightDp.dp / 5
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

            LazyVerticalGrid(cells = GridCells.Fixed(3)){
                items(viewModel.tagState.tags){ item->
                    TagCard(
                        modifier = Modifier.size(
                            width = cardSizeWidth,
                            height = cardSizeHeight
                        ),
                        tag = item,
                        onTagClicked = {
                            Log.d("TAG", "TagScreen: ${it}")
                            onNavigationEditTag(it)
                        }
                    )
                }
            }


        }

    }
}