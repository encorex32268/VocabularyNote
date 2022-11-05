package com.lihan.vocabularynote.presentations.home

import android.util.Log
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
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.presentations.home.components.VocabularyNoteItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigation : (Int) -> Unit
) {
    val spacer = LocalSpacing.current
    viewModel.onEvent(HomeEvent.GetNotes)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigation(-1)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Go to Add")
            }
        }
    ) {
        Column (
            modifier = modifier.fillMaxSize()
        ){
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
                            onNavigation(it)
                        }
                    )
                }
            }

        }
    }




}