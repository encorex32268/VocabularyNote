package com.lihan.vocabularynote.feature.home.presentations.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.core.data.preferences.DefaultPreferences
import com.lihan.vocabularynote.core.domain.model.User
import com.lihan.vocabularynote.core.domain.repository.Preferences
import com.lihan.vocabularynote.feature.home.domain.use_cases.VocabularyNoteUseCases
import com.lihan.vocabularynote.feature.storage.domain.use_cases.StorageUseCases
import com.lihan.vocabularynote.feature.tag.domain.use_cases.TagUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vocabularyNoteUseCases: VocabularyNoteUseCases,
    private val storageUseCases: StorageUseCases,
    private val tagUseCases : TagUseCases
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        onEvent(HomeEvent.GetAllStorage)
        onEvent(HomeEvent.GetAllVocabularyNotes)
        onEvent(HomeEvent.GetAllTags)
        if (state.storages.isNotEmpty()){
            onEvent(HomeEvent.GetNotesByStorageId(storageId = state.storages[0].storageId))
        }
    }
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetAllStorage->{
                viewModelScope.launch {
                    storageUseCases.getStorages.invoke().collectLatest {
                        state = state.copy(
                            storages = it
                        )
                    }
                }
            }
            is HomeEvent.GetAllTags->{
                viewModelScope.launch {
                    tagUseCases.getAllTag.invoke().collectLatest {
                        state = state.copy(
                            tags = it
                        )
                    }
                }
            }
            is HomeEvent.InsertVocabulary->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.insertEditVocabularyNote.invoke(event.vocabularyNote)
                }
            }
            is HomeEvent.DeleteVocabulary->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.deleteVocabularyNote.invoke(event.noteId)
                }
            }
            is HomeEvent.SearchByString ->{
                if (event.string.isNotBlank()){
                    viewModelScope.launch {
                        vocabularyNoteUseCases.getVocabularyByText.invoke(event.string).collectLatest {
                            state = state.copy(
                                notes = it,
                                searchText = event.string
                            )
                        }
                    }
                }else{
                    viewModelScope.launch {
                        vocabularyNoteUseCases.getVocabularyNotes.invoke().collectLatest {
                            state = state.copy(
                                notes = it,
                                searchText = ""
                            )
                        }
                    }

                }
            }

            is HomeEvent.ChangeHintVisible ->{
                state = state.copy(
                    isHintVisible = !event.visible && state.searchText.isBlank()
                )
            }

            is HomeEvent.GetAllVocabularyNotes->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.getVocabularyNotes.invoke().collectLatest {
                        state = state.copy(
                            notes = it
                        )
                    }
                }
            }

        }

    }

}



