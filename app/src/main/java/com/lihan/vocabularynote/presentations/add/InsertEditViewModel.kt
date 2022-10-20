package com.lihan.vocabularynote.presentations.add

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.use_cases.GetVocabularyByNoteId
import com.lihan.vocabularynote.domain.use_cases.InsertEditVocabularyNote
import com.lihan.vocabularynote.domain.use_cases.VocabularyNoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InsertEditViewModel @Inject constructor(
   private val vocabularyNoteUseCases: VocabularyNoteUseCases
) : ViewModel(){

    private var currentNoteId: Int? = null


    var state by  mutableStateOf(
        VocabularyNote(
            id = currentNoteId,
            type = VocabularyNote.typeColors.random().toArgb(),
            word = "",
            hiraganaOrKatakana = "",
            roma = "",
            createDate = System.currentTimeMillis(),
            explain = ""
        )
    )
        private set



    fun  onEvent(event : InsertEditEvent){
        when(event){
            is InsertEditEvent.IsEditPage->{
                viewModelScope.launch {
                    currentNoteId = event.id
                    vocabularyNoteUseCases.getVocabularyByNoteId(event.id)?.let {
                        state = state.copy(
                            type =  it.type,
                            word = if (it.word.isEmpty()){
                                it.hiraganaOrKatakana
                            }else{ it.word },
                            hiraganaOrKatakana = if (it.word.isNotEmpty()){
                                it.hiraganaOrKatakana
                            }else{ "" },
                            roma = it.roma,
                            createDate = it.createDate,
                            explain = it.explain
                        )
                    }
                }


            }
            is InsertEditEvent.Save->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.insertEditVocabularyNote(
                        vocabularyNote = VocabularyNote(
                            id = currentNoteId,
                            type = state.type,
                            word = state.word,
                            hiraganaOrKatakana = state.hiraganaOrKatakana,
                            roma = state.roma,
                            createDate = state.createDate,
                            explain = state.explain
                        )
                    )
                }
            }
            is InsertEditEvent.Remove->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.deleteVocabularyNote(
                        noteId = event.removeId
                    )

                }
            }
            is InsertEditEvent.TypeColorChanged->{
                state = state.copy(
                    type = event.color.toArgb()
                )
            }
            is InsertEditEvent.WordChanged->{
                state = state.copy(
                    word = event.word
                )
            }
            is InsertEditEvent.HiraganaChanged->{
                state = state.copy(
                    hiraganaOrKatakana = event.hiragana
                )
            }
            is InsertEditEvent.ExplainChanged->{
                state = state.copy(
                    explain = event.explain
                )
            }

        }
    }


}