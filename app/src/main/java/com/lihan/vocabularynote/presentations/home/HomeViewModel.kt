package com.lihan.vocabularynote.presentations.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.domain.use_cases.VocabularyNoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vocabularyNoteUseCases: VocabularyNoteUseCases
) : ViewModel() {

    private var getVocabularyNotesJob: Job? = null

    var state by mutableStateOf(HomeState())
        private set

    init {
        getData()
    }

    private fun getData(){
        getVocabularyNotesJob?.cancel()
        getVocabularyNotesJob = vocabularyNoteUseCases.getVocabularyNotes.invoke()
            .onEach {
                state = state.copy(
                    notes = it
                )
            }
            .launchIn(viewModelScope)

    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetNotes -> {
                getData()
            }
            is HomeEvent.SortByColor->{
                val data = state.notes
                state = state.copy(
                    notes = data.filter { note ->
                        note.type == event.color.toArgb()
                    }
                )
            }
            is HomeEvent.SearchByString->{
                val data = state.notes
                if (event.string.isNotBlank()){
                    state = state.copy(
                        notes = data.filter { note ->
                            note.hiraganaOrKatakana.contains(event.string) ||
                                    note.word.contains(event.string) },
                        searchText = event.string
                    )
                }
                else{
                    getData()
                    state = state.copy(
                        searchText = ""
                    )
                }
            }

            is HomeEvent.ChangeHintVisible->{
                state = state.copy(
                    isHintVisible = !event.visible && state.searchText.isBlank()
                )
            }


            }

        }

}



