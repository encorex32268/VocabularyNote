package com.lihan.vocabularynote.feature.storage.presentations.edit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.core.util.UiEvent
import com.lihan.vocabularynote.feature.home.domain.use_cases.VocabularyNoteUseCases
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.domain.use_cases.StorageUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StorageEditViewModel @Inject constructor(
    private val vocabularyNoteUseCases: VocabularyNoteUseCases,
    private val storageUseCases: StorageUseCases
)  : ViewModel(){

    var state by mutableStateOf(StorageEditState())
    private val _uiEvent = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: StorageEditEvent){
        when(event){
            is StorageEditEvent.DeleteVocabulary->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.deleteVocabularyNote(event.noteId)
                }
            }
            is StorageEditEvent.UpdateVocabulary->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.updateVocabularyNote(event.vocabularyNote)
                }
            }
            is StorageEditEvent.UpdateStorage->{
                viewModelScope.launch {
                    storageUseCases.updateStorage(state.storage)
                }
            }
            is StorageEditEvent.InsertVocabulary->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.insertEditVocabularyNote(event.vocabularyNote)
                }
            }
            is StorageEditEvent.GetStorage->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.getVocabularyByStorageId
                        .invoke(event.storageId)
                        .collectLatest {
                            state = state.copy(
                                items = it,
                                storage = storageUseCases.getStorageById(event.storageId)?: Storage()
                            )
                        }
                }
            }
            is StorageEditEvent.ChangeStorageName->{
                state = state.copy(
                    storage = state.storage.copy(
                        name = event.text
                    )
                )
            }
            is StorageEditEvent.ChangeStorageDescription->{
                state = state.copy(
                    storage = state.storage.copy(
                        description = event.description
                    )
                )
            }
            is StorageEditEvent.DeleteStorage->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.deleteVocabularyNoteByStorageId(event.storageId)
                    storageUseCases.deleteStorage(event.storageId)
                    _uiEvent.send(UiEvent.Success)
                }
            }

        }
    }


}