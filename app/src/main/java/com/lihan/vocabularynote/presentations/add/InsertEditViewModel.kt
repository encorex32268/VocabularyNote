package com.lihan.vocabularynote.presentations.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.Preferences
import com.lihan.vocabularynote.domain.use_cases.vocabulary.VocabularyNoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InsertEditViewModel @Inject constructor(
    private val vocabularyNoteUseCases: VocabularyNoteUseCases,
    private val preferences: Preferences
) : ViewModel(){

    private var currentNoteId: Int? = null



    var state by  mutableStateOf(
        VocabularyNote(
            id = currentNoteId,
            type =preferences.loadUserColorWhenAdd().toArgb(),
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
            is InsertEditEvent.IsAddPage->{
                state = state.copy(

                )
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
                preferences.saveUserColorWhenAdd(event.color)
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