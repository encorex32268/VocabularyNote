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

    private var getVocabularyNoteJob: Job? = null


    var state by  mutableStateOf(InsertEditState())
        private set




    fun  onEvent(event : InsertEditEvent){
        when(event){
            is InsertEditEvent.IsEditPage->{
                getVocabularyNoteJob?.cancel()
                getVocabularyNoteJob = vocabularyNoteUseCases
                    .getVocabularyByNoteId(event.id)
                    .onEach {
                        state = state.copy(
                            typeColor =  it.type,
                            word = if (it.word.isEmpty()){
                                it.hiraganaOrKatakana
                            }else{ it.word },
                            hiragana = if (it.word.isNotEmpty()){
                                it.hiraganaOrKatakana
                            }else{ "" },
                            roma = it.roma,
                            createDate = it.createDate,
                            explain = it.explain
                        )
                }.launchIn(viewModelScope)
            }
            is InsertEditEvent.Save->{
                viewModelScope.launch {
                    vocabularyNoteUseCases.insertEditVocabularyNote(
                        vocabularyNote = VocabularyNote(
                            type = state.typeColor,
                            word = state.word,
                            hiraganaOrKatakana = state.hiragana,
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
                        vocabularyNote = VocabularyNote(
                            id = event.removeId,
                            type = state.typeColor,
                            word = state.word,
                            hiraganaOrKatakana = state.hiragana,
                            roma = state.roma,
                            createDate = state.createDate,
                            explain = state.explain
                        )
                    )
                }
            }
            is InsertEditEvent.TypeColorChanged->{
                state = state.copy(
                    typeColor = event.color.toArgb()
                )
            }
            is InsertEditEvent.WordChanged->{
                state = state.copy(
                    word = event.word
                )
            }
            is InsertEditEvent.HiraganaChanged->{
                state = state.copy(
                    hiragana = event.hiragana
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