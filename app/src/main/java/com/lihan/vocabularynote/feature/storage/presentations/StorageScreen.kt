package com.lihan.vocabularynote.feature.storage.presentations

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.core.presentations.componets.SearchBar
import com.lihan.vocabularynote.core.presentations.componets.VocabularyTextField
import com.lihan.vocabularynote.core.util.UiEvent
import com.lihan.vocabularynote.feature.home.presentations.home.HomeEvent
import com.lihan.vocabularynote.feature.home.presentations.home.components.VocabularyNoteItem
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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
                                .clickable(
                                    onClick = {
                                        onEvent(StorageEvent.GetNotesByStorageId(it.storageId))
                                    }
                                )
                            ,
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor = Color.White,
                            border = BorderStroke(
                                width = 1.dp, color = Color.Black
                            )
                        ) {
                            Text(
                                modifier = Modifier.padding(spacer.spaceExtraSmall),
                                text = it.name
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(spacer.spaceMedium))
                if (state.notes.isEmpty()){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = stringResource(id = R.string.storage_empty_data)
                        )
                    }
                }else{
                    LazyColumn{
                        items(
                            state.notes,
                            key = {
                                it.hashCode()
                            }
                        ){
                            VocabularyNoteItem(
                                vocabularyNote = it,
                                onItemClick = {}
                            )
                        }
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