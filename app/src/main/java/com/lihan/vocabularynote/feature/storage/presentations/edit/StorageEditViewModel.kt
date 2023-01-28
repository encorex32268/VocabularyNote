package com.lihan.vocabularynote.feature.storage.presentations.edit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.feature.home.domain.use_cases.VocabularyNoteUseCases
import com.lihan.vocabularynote.feature.storage.domain.use_cases.StorageUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StorageEditViewModel @Inject constructor(
    private val vocabularyNoteUseCases: VocabularyNoteUseCases,
    private val storageUseCases: StorageUseCases
)  : ViewModel(){

    var state by mutableStateOf(StorageEditState())


    fun onEvent(event: StorageEditEvent){
        when(event){
            is StorageEditEvent.GetVocabularyByStorageId->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.getVocabularyByStorageId
                        .invoke(event.storageId)
                        .collectLatest {
                            state = state.copy(
                                items = it
                        )
                    }
                }
            }
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
                    storageUseCases.updateStorage(event.storage)
                }
            }
            is StorageEditEvent.InsertVocabulary->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.insertEditVocabularyNote(event.vocabularyNote)
                }
            }

        }
    }


}