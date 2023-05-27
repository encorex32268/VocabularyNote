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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.core.util.UiEvent
import com.lihan.vocabularynote.feature.home.presentations.home.components.MultipleActionItem
import com.lihan.vocabularynote.feature.home.presentations.home.components.MultipleFloatingActionButton
import com.lihan.vocabularynote.feature.home.presentations.home.components.VocabularyNoteItem
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import java.text.DateFormat
import java.text.SimpleDateFormat

@Composable
fun StorageEditScreen(
    modifier: Modifier = Modifier,
    onCloseButtonClicked : () -> Unit ,
    storage : Storage,
    state : StorageEditState,
    uiEvent : Flow<UiEvent>,
    onEvent : (StorageEditEvent) -> Unit,
    onNewVocabularyNoteClicked : (Int) -> Unit,
    onEditVocabularyNoteClicked : (Int,Int) -> Unit
) {
    val spacer = LocalSpacing.current
    val focusManager = LocalFocusManager.current
    var isShowDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = storage.storageId){
            onEvent(StorageEditEvent.GetStorage(storageId = storage.storageId))
    }
    LaunchedEffect(key1 = true){
        uiEvent.collectLatest {
            if (it is UiEvent.Success){
                onCloseButtonClicked()
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(StorageEditEvent.UpdateStorage)
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier
                        .size(64.dp),
                    onClick = onCloseButtonClicked) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "StorageEditBack"
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(64.dp),
                    onClick = {
                        isShowDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "StorageEditDelete"
                    )
                }
            }
            if (isShowDialog){
                AlertDialog(
                    onDismissRequest = {
                         isShowDialog = false
                    },
                    title = {
                        Text(text = stringResource(id = R.string.storage_edit_delete_dialog_title))
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            isShowDialog = false
                            onEvent(StorageEditEvent.DeleteStorage(storage.storageId))
                        }) {
                            Text(text = stringResource(id = R.string.storage_edit_delete_dialog_OK_button))
                        }
                    },
                    text = {
                        Text(text = stringResource(id = R.string.storage_edit_delete_dialog_message))
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            isShowDialog = false
                        }) {
                            Text(text = stringResource(id = R.string.storage_edit_delete_dialog_cancel_button))
                        }
                    }
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
                                      onEvent(StorageEditEvent.UpdateStorage)
                                  }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        ),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h4,
                        value = state.storage.name,
                        onValueChange = {
                            onEvent(StorageEditEvent.ChangeStorageName(it))
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onEvent(StorageEditEvent.UpdateStorage)
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
                                onEvent(StorageEditEvent.UpdateStorage)
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        ),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1,
                        value = state.storage.description,
                        onValueChange = {
                            onEvent(StorageEditEvent.ChangeStorageDescription(it))
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onEvent(StorageEditEvent.UpdateStorage)
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
                        text = stringResource(id = R.string.storage_edit_createAt , getRealTime(storage.createAt)),
                        style = MaterialTheme.typography.body1
                    )
                    Text(text = stringResource(id = R.string.storage_edit_count , state.items.size))
                }
            }
            LazyColumn{
                items(state.items){ note->
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


@Preview
@Composable
fun StorageEditScreenPreView(){
    VocabularyNoteTheme {
        val storage = Storage(
            name = "1234",
            description = "12314"
        )
        StorageEditScreen(
            onCloseButtonClicked = { },
            storage = storage,
            state = StorageEditState(
                storage = storage
            ),
            uiEvent = flow {  },
            onEvent = {event ->},
            onNewVocabularyNoteClicked = { temp ->},
            onEditVocabularyNoteClicked = {temp , temp1 ->}
        )
    }
}