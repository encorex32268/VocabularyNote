package com.lihan.vocabularynote.presentations.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.vocabularynote.core.util.Resource
import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository
import com.lihan.vocabularynote.domain.use_cases.GetVocabularyNotes
import com.lihan.vocabularynote.domain.use_cases.VocabularyNoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vocabularyNoteUseCases: VocabularyNoteUseCases
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set
    init {
        onEvent(HomeEvent.GetNotes)

    }

    fun onEvent(event : HomeEvent){
        when(event){
            HomeEvent.GetNotes->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.getVocabularyNotes.invoke().onEach {
                                state = state.copy(
                                    notes = it
                                )
                            }
                    }

                }

            }
        }
    }



