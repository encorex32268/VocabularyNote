package com.lihan.vocabularynote.presentations.home

import android.inputmethodservice.Keyboard.Row
import android.util.Log
import android.view.WindowInsets
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.presentations.home.components.MultipleActionItem
import com.lihan.vocabularynote.presentations.home.components.MultipleFloatingActionButton
import com.lihan.vocabularynote.presentations.home.components.SearchBar
import com.lihan.vocabularynote.presentations.home.components.VocabularyNoteItem
import java.text.SimpleDateFormat

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigationToAddEdit : (Int) -> Unit,
    onNavigationToExam : () -> Unit
) {
    val spacer = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    viewModel.onEvent(HomeEvent.GetNotes)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            MultipleFloatingActionButton(
                multipleActionItems = listOf(
                    MultipleActionItem(
                        name = Route.ADD_EDIT,
                        icon = Icons.Default.Add
                    ),
                    MultipleActionItem(
                        name = Route.EXAM,
                        icon = Icons.Default.List
                    )

                ),
                onFloatingButtonClick = {
                    when(it.name){
                        Route.EXAM->{
                            onNavigationToExam()
                        }
                        Route.ADD_EDIT->{
                            focusManager.clearFocus()
                            onNavigationToAddEdit(-1)
                        }
                    }
                }
            )
        }
    ) {
        Column (
            modifier = modifier.fillMaxSize()
        ){
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacer.spaceExtraSmall)
                ,
                onValueChange = {
                    viewModel.onEvent(HomeEvent.SearchByString(it))
                },
                onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    viewModel.onEvent(HomeEvent.SearchByString(viewModel.state.searchText))
                },
                text = viewModel.state.searchText,
                onFocusChanged = {
                    viewModel.onEvent(HomeEvent.ChangeHintVisible(it.isFocused))
                },
                shouldShowHint = viewModel.state.isHintVisible
            )
            Spacer(modifier = Modifier.height(spacer.spaceExtraSmall))
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
                    ){
                Box(
                    modifier = Modifier
                        .padding(spacer.spaceSmall)
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colors.secondary,
                            shape = CircleShape
                        )
                ){
                    IconButton(
                        onClick = { viewModel.onEvent(HomeEvent.GetNotes) })
                    {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )

                    }
                }
                LazyRow{
                  items(VocabularyNote.typeColors){ color ->
                      Box(
                          modifier = Modifier
                              .padding(spacer.spaceSmall)
                              .size(40.dp)
                              .background(
                                  color = color,
                                  shape = CircleShape
                              )
                              .pointerInput(true) {
                                  detectTapGestures {
                                      viewModel.onEvent(HomeEvent.SortByColor(color))
                                  }
                              }
                      )
                  }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn{
                items(viewModel.state.notes){ note ->
                    VocabularyNoteItem(
                        vocabularyNote = note,
                        onItemClick = {
                            onNavigationToAddEdit(it)
                        }
                    )
                }
            }

        }
    }




}