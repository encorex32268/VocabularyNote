package com.lihan.vocabularynote.presentations.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.presentations.home.components.VocabularyNoteItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigation : (Int) -> Unit
) {
    val state = viewModel.state
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
        Box (
            modifier = modifier.fillMaxSize()
        ){
            LazyColumn{
                items(state.notes){ note ->
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