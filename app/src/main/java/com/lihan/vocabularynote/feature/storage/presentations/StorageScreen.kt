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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.core.presentations.componets.SearchBar
import com.lihan.vocabularynote.core.util.UiEvent
import com.lihan.vocabularynote.feature.home.presentations.home.HomeEvent
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Composable
fun StorageScreen(
    onCloseButtonClicked : () -> Unit,
    onEditStorageClicked : (Storage) -> Unit,
    modifier: Modifier = Modifier,
    viewModel : StorageViewModel = hiltViewModel()
) {
    val spacer = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {
            if (it == UiEvent.Success){
                scrollState.animateScrollToItem(viewModel.storageState.items.size - 1)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
        ){
            IconButton(
                modifier = Modifier
                    .size(64.dp)
                    .padding(spacer.spaceMedium)
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
                text = Route.STORAGE
            )
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
                        .weight(9f)
                        .padding(spacer.spaceSmall)
                    ,
                    onValueChange = {
                        viewModel.onEvent(StorageEvent.SearchStorage(it))
                    },
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        viewModel.onEvent(StorageEvent.SearchStorage(viewModel.storageState.searchText))
                    },
                    text = viewModel.storageState.searchText,
                    onFocusChanged = {
                        viewModel.onEvent(StorageEvent.ChangeHintVisible(it.isFocused))
                    },
                    shouldShowHint = viewModel.storageState.isHintVisible
                )


                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Default.Filter1,
                    contentDescription = "FilterBAndW"
                )
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Default.Filter,
                    contentDescription = "Filter"
                )
            }
            Spacer(modifier = Modifier.height(spacer.spaceSmall))
            LazyColumn(
                state = scrollState
            ){
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacer.spaceSmall)
                            .clickable {
                                viewModel.onEvent(StorageEvent.InsertStorage)
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
                                text = "New",
                                style = MaterialTheme.typography.body2,
                                color = Color.Black
                            )
                        }
                    }
                }
                items(viewModel.storageState.items){ storage ->
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