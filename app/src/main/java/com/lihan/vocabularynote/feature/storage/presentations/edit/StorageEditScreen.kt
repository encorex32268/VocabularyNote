package com.lihan.vocabularynote.feature.storage.presentations.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.home.presentations.home.components.MultipleActionItem
import com.lihan.vocabularynote.feature.home.presentations.home.components.MultipleFloatingActionButton
import com.lihan.vocabularynote.feature.home.presentations.home.components.VocabularyNoteItem
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

@Composable
fun StorageEditScreen(
    modifier: Modifier = Modifier,
    onCloseButtonClicked : () -> Unit ,
    storage : Storage,
    viewModel: StorageEditViewModel = hiltViewModel(),
    onNewVocabularyNoteClicked : (Int) -> Unit,
    onEditVocabularyNoteClicked : (Int,Int) -> Unit
) {
    val spacer = LocalSpacing.current
    viewModel.onEvent(StorageEditEvent.GetVocabularyByStorageId(storageId = storage.storageId))
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNewVocabularyNoteClicked(storage.storageId)
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "VocabularyNoteAddInStorage"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier
                    .size(64.dp)
                    .align(End),
                onClick = onCloseButtonClicked) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "StorageEditClose"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacer.spaceMedium, end = spacer.spaceMedium)
            ) {
                Text(
                    text = storage.name,
                    style = MaterialTheme.typography.h3
                )
                Text(
                    text = "Description:${storage.description}",
                    style = MaterialTheme.typography.body1
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "CreateAt:${storage.createAt}",
                        style = MaterialTheme.typography.body1
                    )
                    Text(text = "Count:${viewModel.state.items.size}")
                }
            }
            LazyColumn{
                items(viewModel.state.items){ note->
                    VocabularyNoteItem(
                        vocabularyNote = note,
                        onEditClick = {
                            onEditVocabularyNoteClicked(note.id?:0,note.storageId)
                        },
                        isShowEdit = true
                    )
                }
            }




        }


    }




}

fun getRealTime(createAt: Long): String {
    var result = ""
    return result
}
