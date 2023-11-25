package com.lihan.vocabularynote.feature.storage.presentations

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.presentations.componets.VocabularyTextField
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.presentations.home.components.VocabularyNoteItem
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun StorageScreen(
    state : StorageState,
    onEvent : (StorageEvent) -> Unit = {}
) {
    val spacer = LocalSpacing.current
    var isShowAddNewStorage by remember{
        mutableStateOf(false)
    }
    var isShowDeleteStorage by remember {
        mutableStateOf(false)
    }
    var selectedStorageId by remember {
        mutableStateOf<Int?>(null)
    }
    var isShowDeleteNote by remember {
        mutableStateOf(false)
    }
    var selectedNote by remember {
        mutableStateOf<VocabularyNote?>(null)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isShowAddNewStorage = !isShowAddNewStorage
            }) {
                Icon(
                    imageVector = Icons.Filled.CreateNewFolder,
                    contentDescription = null
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Column(Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TitleText(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = R.string.storage)
                    )
                }
                Spacer(modifier = Modifier.height(spacer.spaceMedium))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = spacer.spaceSmall
                        )
                ){
                    items(
                        state.items,
                        key = {
                            it.hashCode()
                        }
                    ){
                        Card(
                            modifier = Modifier
                                .padding(spacer.spaceSmall)
                                .combinedClickable(
                                    onClick = {
                                        selectedStorageId = it.storageId
                                        onEvent(StorageEvent.GetNotesByStorageId(it.storageId))
                                    },
                                    onLongClick = {
                                        selectedStorageId = it.storageId
                                        isShowDeleteStorage = true
                                    }
                                )
                            ,
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor =  if (selectedStorageId == it.storageId) Color.Black else Color.White,
                            border = BorderStroke(
                                width = 1.dp, color =  Color.Black
                            )
                        ) {
                            Text(
                                modifier = Modifier.padding(spacer.spaceSmall),
                                text = it.name,
                                color = if (selectedStorageId == it.storageId) Color.White else Color.Black
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(spacer.spaceMedium))
                LazyColumn{
                    items(
                        items = state.notes,
                        key = {
                            it.hashCode()
                        }
                    ){
                        VocabularyNoteItem(
                            vocabularyNote = it,
                            onItemClick = {},
                            onLongClick = {
                                selectedNote = it
                                isShowDeleteNote = true
                            }
                        )
                    }
                }
            }
            if (isShowAddNewStorage){
                AddNewStorageDialog(
                    onDismiss = {isShowAddNewStorage = false},
                    onOkClick = { title , description ->
                        onEvent(
                            StorageEvent.InsertStorage(
                                title = title,
                                description = description
                            )
                        )
                    }
                )
            }
            if (isShowDeleteStorage){
                DeleteStorageDialog(
                    onDismiss = {
                        isShowDeleteStorage = false
                                },
                    onOkClick = {
                        selectedStorageId = null
                        onEvent(
                            StorageEvent.DeleteStorage(it)
                        )
                    },
                    storageId = selectedStorageId
                )
            }
            if (isShowDeleteNote){
                selectedNote?.let {
                    DeleteNoteDialog(
                        id = it.id,
                        onDismiss = {
                            selectedNote  = null
                        },
                        onOkClick = {
                            it.id?.let { id ->
                                onEvent(StorageEvent.DeleteVocabulary(id))
                                isShowDeleteNote = false

                            }

                        }
                    )
                }
            }
        }

    }

}

@ExperimentalComposeUiApi
@Composable
private fun AddNewStorageDialog(
    onDismiss : () -> Unit = {},
    onOkClick : (String,String) -> Unit = {_,_ ->}
){
    val spacer = LocalSpacing.current
    var storageName by remember {
        mutableStateOf("")
    }
    var storageDec by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
                .padding(spacer.spaceSmall)
        ) {
            Spacer(modifier = Modifier.height(spacer.spaceSmall))
            Text(
                text = stringResource(id = R.string.storage_add_title)
            )
            Spacer(modifier = Modifier.height(spacer.spaceExtraSmall))
            VocabularyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacer.spaceSmall),
                text = storageName,
                onValueChange = {
                    storageName = it
                },
                onDone = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
            Spacer(modifier = Modifier.height(spacer.spaceExtraSmall))
            Text(text = stringResource(id = R.string.storage_add_description))
            VocabularyTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacer.spaceSmall),
                text = storageDec ,
                onValueChange ={
                    storageDec = it
                },
                onDone = {
                    keyboard?.hide()
                    focusManager.clearFocus(true)
                }
            )
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(spacer.spaceSmall),
                onClick = {
                onOkClick(storageName,storageDec)
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.storage_edit_delete_dialog_OK_button))
            }
        }
    }

}

@Composable
private fun DeleteNoteDialog(
    id : Int?=null,
    onDismiss: () -> Unit = {},
    onOkClick : () -> Unit = {}
){
    id?.let {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            title = {
                Text(text = stringResource(id = R.string.storage_edit_delete_dialog_title))
            },
            confirmButton = {
                TextButton(onClick = {
                    onOkClick()
                    onDismiss()
                }) {
                    Text(text = stringResource(id = R.string.storage_edit_delete_dialog_OK_button))
                }
            },
            text = {
                Text(text = stringResource(id = R.string.storage_edit_delete_dialog_message))
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = stringResource(id = R.string.storage_edit_delete_dialog_cancel_button))
                }
            }
        )
    }
}

@Composable
private fun DeleteStorageDialog(
    storageId : Int?=null,
    onDismiss: () -> Unit = {},
    onOkClick : (Int) -> Unit = {}
){
    storageId?.let {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            title = {
                Text(text = stringResource(id = R.string.storage_edit_delete_dialog_title))
            },
            confirmButton = {
                TextButton(onClick = {
                    onOkClick(storageId)
                    onDismiss()
                }) {
                    Text(text = stringResource(id = R.string.storage_edit_delete_dialog_OK_button))
                }
            },
            text = {
                Text(text = stringResource(id = R.string.storage_edit_delete_dialog_message))
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = stringResource(id = R.string.storage_edit_delete_dialog_cancel_button))
                }
            }
        )
    }
}




@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Preview(showSystemUi = true)
@Composable
fun StorageScreenPreView(){
    VocabularyNoteTheme {
        AddNewStorageDialog()
//        StorageScreen(
//            state = StorageState(
//                items = listOf(
//                    Storage(
//                        name = "123",
//                        description = "New 1",
//                        createAt = 123
//                    ),
//                    Storage(
//                        name = "456",
//                        description = "New 2",
//                        createAt = 456
//                    ),
//                )
//            )
//        )
//        StorageScreen(
//            onCloseButtonClicked = {  },
//            onEditStorageClicked = { storage ->},
//            storageState = StorageState(
//                searchText = "123",
//                items = listOf(
//                    Storage(
//                        name = "123",
//                        description = "New 1",
//                        createAt = 123
//                    ),
//                    Storage(
//                        name = "456",
//                        description = "New 2",
//                        createAt = 456
//                    ),
//                )
//            ),
//            uiEvent = flow {  },
//            onEvent = { event ->}
//        )

    }
}