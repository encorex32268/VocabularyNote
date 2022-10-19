package com.lihan.vocabularynote.presentations.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.domain.use_cases.VocabularyNoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
        onEvent(HomeEvent.GetNotes)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetNotes -> {
                getVocabularyNotesJob?.cancel()
                getVocabularyNotesJob = vocabularyNoteUseCases.getVocabularyNotes.invoke()
                    .onEach {
                        state = state.copy(
                            notes = it
                        )
                    }.launchIn(viewModelScope)

            }
        }
    }
}


