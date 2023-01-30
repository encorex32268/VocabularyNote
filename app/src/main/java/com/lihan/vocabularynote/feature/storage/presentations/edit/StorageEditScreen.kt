package com.lihan.vocabularynote.feature.storage.presentations.edit

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.home.presentations.home.components.MultipleActionItem
import com.lihan.vocabularynote.feature.home.presentations.home.components.MultipleFloatingActionButton
import com.lihan.vocabularynote.feature.home.presentations.home.components.VocabularyNoteItem
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import java.text.DateFormat
import java.text.SimpleDateFormat

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
    val focusManager = LocalFocusManager.current
    val state = viewModel.state
    LaunchedEffect(key1 = storage.storageId){
        viewModel.apply {
            onEvent(StorageEditEvent.GetVocabularyByStorageId(storageId = storage.storageId))
            onEvent(StorageEditEvent.InitStorage(storage = storage))
        }
    }

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
                    .align(Start),
                onClick = onCloseButtonClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "StorageEditBack"
                )
            }
            IconButton(
                modifier = Modifier
                    .size(64.dp)
                    .align(End),
                onClick = {
                    viewModel.onEvent(StorageEditEvent.DeleteStorage(storage.storageId))
                    onCloseButtonClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "StorageEditDelete"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacer.spaceMedium, end = spacer.spaceMedium)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier.onFocusChanged {
                                  if(!it.hasFocus){
                                      viewModel.onEvent(StorageEditEvent.UpdateStorage)
                                  }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        ),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h4,
                        value = viewModel.state.storage.name,
                        onValueChange = {
                            viewModel.onEvent(StorageEditEvent.ChangeStorageName(it))
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                viewModel.onEvent(StorageEditEvent.UpdateStorage)
                                focusManager.clearFocus()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier.onFocusChanged {
                            if(!it.hasFocus){
                                viewModel.onEvent(StorageEditEvent.UpdateStorage)
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        ),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1,
                        value = viewModel.state.storage.description,
                        onValueChange = {
                            viewModel.onEvent(StorageEditEvent.ChangeStorageDescription(it))
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                viewModel.onEvent(StorageEditEvent.UpdateStorage)
                                focusManager.clearFocus()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(spacer.spaceSmall))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "CreateAt: ${getRealTime(storage.createAt)}",
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
    val simpleFormatter = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss",createAt)
    return simpleFormatter.toString()
}
