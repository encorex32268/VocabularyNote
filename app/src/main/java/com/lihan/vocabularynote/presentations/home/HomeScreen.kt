package com.lihan.vocabularynote.presentations.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.presentations.home.components.VocabularyNoteItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Box (
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            ){
        LazyColumn{
            items(state.notes){ note ->
                VocabularyNoteItem(vocabularyNote = note)
            }
        }

    }



}