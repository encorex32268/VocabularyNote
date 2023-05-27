package com.lihan.vocabularynote.feature.storage.presentations

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.core.presentations.componets.SearchBar
import com.lihan.vocabularynote.core.util.UiEvent
import com.lihan.vocabularynote.feature.home.presentations.home.HomeEvent
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@ExperimentalComposeUiApi
@Composable
fun StorageScreen(
    onCloseButtonClicked : () -> Unit,
    onEditStorageClicked : (Storage) -> Unit,
    modifier: Modifier = Modifier,
    storageState : StorageState,
    uiEvent : Flow<UiEvent>,
    onEvent : (StorageEvent) -> Unit
) {
    val spacer = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = true){
        uiEvent.collect {
            if (it == UiEvent.Success){
                scrollState.animateScrollToItem(
                    storageState.items.size
                )
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TitleText(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.storage)
                )
                IconButton(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(spacer.spaceMedium),
                    onClick = {
                        onCloseButtonClicked()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
            Spacer(modifier = Modifier.height(spacer.spaceSmall))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacer.spaceMedium)
                ,
                verticalAlignment = Alignment.CenterVertically
            ){

                SearchBar(
                    modifier = Modifier
                        .weight(1f)
                        .padding(spacer.spaceSmall)
                    ,
                    onValueChange = {
                        onEvent(StorageEvent.SearchStorage(it))
                    },
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onEvent(
                            StorageEvent.SearchStorage(
                            storageState.searchText)
                        )
                    },
                    text = storageState.searchText,
                    onFocusChanged = {
                        onEvent(StorageEvent.ChangeHintVisible(it.isFocused))
                    },
                    shouldShowHint = storageState.isHintVisible,
                    hintText = stringResource(id = R.string.storage_search_storage)
                )
            }
            Spacer(modifier = Modifier.height(spacer.spaceSmall))
            val title = stringResource(id = R.string.storage_add_title)
            val description = stringResource(id = R.string.storage_add_description)
            LazyColumn(
                state = scrollState
            ){
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacer.spaceSmall)
                            .clickable {
                                onEvent(
                                    StorageEvent.InsertStorage(
                                        title = title,
                                        description = description
                                    )
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "GoToStorageAdd",
                                tint = Color.Black
                            )
                            Text(
                                text = stringResource(id = R.string.storage_add),
                                style = MaterialTheme.typography.body2,
                                color = Color.Black
                            )
                        }
                    }
                }
                items(storageState.items){ storage ->
                    StorageItem(
                        storage = storage,
                        onClick = {
                            onEditStorageClicked(storage)
                        }
                    )
                }

            }




        }

    }

}

@ExperimentalComposeUiApi
@Preview
@Composable
fun StorageScreenPreView(){
    VocabularyNoteTheme {
        StorageScreen(
            onCloseButtonClicked = {  },
            onEditStorageClicked = { storage ->},
            storageState = StorageState(
                searchText = "123",
                items = listOf(
                    Storage(
                        name = "123",
                        description = "New 1",
                        createAt = 123
                    ),
                    Storage(
                        name = "456",
                        description = "New 2",
                        createAt = 456
                    ),
                )
            ),
            uiEvent = flow {  },
            onEvent = { event ->}
        )

    }
}