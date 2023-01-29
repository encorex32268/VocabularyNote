package com.lihan.vocabularynote.feature.home.presentations.add

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.core.domain.repository.Preferences
import com.lihan.vocabularynote.feature.home.domain.use_cases.VocabularyNoteUseCases
import com.lihan.vocabularynote.feature.tag.domain.use_cases.TagUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject


@HiltViewModel
class InsertEditViewModel @Inject constructor(
    private val vocabularyNoteUseCases: VocabularyNoteUseCases,
    private val tagUseCases: TagUseCases,
    private val preferences: Preferences
) : ViewModel(){

    private var currentNoteId: Int? = null



    var state by  mutableStateOf(
        InsertEditState(
            vocabularyNote = VocabularyNote(
                id = currentNoteId,
                type =preferences.loadUserColorWhenAdd().toArgb(),
                word = "",
                hiraganaOrKatakana = "",
                roma = "",
                createDate = Instant.now().toEpochMilli(),
                explain = "",
                storageId = 0
            )
        )

    )
        private set

    init {
        viewModelScope.launch {
            tagUseCases.getAllTag.invoke().collectLatest{
                state = state.copy(
                    tags = it
                )
            }
        }
    }



    fun  onEvent(event : InsertEditEvent){
        when(event){
            is InsertEditEvent.IsEditPage ->{
                viewModelScope.launch {
                    currentNoteId = event.id
                    vocabularyNoteUseCases.getVocabularyByNoteId(event.id)?.let {
                        state = state.copy(
                            vocabularyNote = it
                        )
                    }
                }
            }
            is InsertEditEvent.IsAddPage ->{
                state = state.copy(
                    vocabularyNote = state.vocabularyNote?.copy(
                        storageId = event.storageId
                    )
                )
            }
            is InsertEditEvent.Save ->{
                viewModelScope.launch {
                    state.vocabularyNote?.let {
                        vocabularyNoteUseCases.insertEditVocabularyNote(it)
                    }
                }
            }
            is InsertEditEvent.Remove ->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.deleteVocabularyNote(
                        noteId = event.removeId
                    )

                }
            }
            is InsertEditEvent.TypeColorChanged ->{
                state = state.copy(
                    vocabularyNote = state.vocabularyNote?.copy(
                        type = event.colorArgb
                    )
                )
                preferences.saveUserColorWhenAdd(event.colorArgb)
            }
            is InsertEditEvent.WordChanged ->{
                state = state.copy(
                    vocabularyNote = state.vocabularyNote?.copy(
                        word = event.word
                    )
                )
            }
            is InsertEditEvent.HiraganaChanged ->{
                state = state.copy(
                    vocabularyNote = state.vocabularyNote?.copy(
                        hiraganaOrKatakana = event.hiragana
                    )
                )
            }
            is InsertEditEvent.ExplainChanged ->{
                state = state.copy(
                    vocabularyNote = state.vocabularyNote?.copy(
                        explain = event.explain
                    )
                )
            }

        }
    }


}