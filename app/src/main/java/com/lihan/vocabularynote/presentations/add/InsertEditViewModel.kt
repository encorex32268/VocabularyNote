package com.lihan.vocabularynote.presentations.add

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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InsertEditViewModel @Inject constructor(
   private val vocabularyNoteUseCases: VocabularyNoteUseCases
) : ViewModel(){

    var state by  mutableStateOf(
            VocabularyNote(
                id = -1 , type = -16777216 , word = "" ,
                hiraganaOrKatakana =  "" , roma = "" ,
                createDate = System.currentTimeMillis(), explain = ""
            )
    )
        private set




    fun  onEvent(event : InsertEditEvent){
        when(event){
            is InsertEditEvent.IsEditPage->{
                viewModelScope.launch {
                    val note = vocabularyNoteUseCases.getVocabularyByNoteId.invoke(event.id)
                    note.onEach {
                        state = state.copy(
                            id = it.id,
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
                    vocabularyNoteUseCases.insertEditVocabularyNote.invoke(state)
                }
            }
            is InsertEditEvent.Remove->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.deleteVocabularyNote.invoke(state.id)
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