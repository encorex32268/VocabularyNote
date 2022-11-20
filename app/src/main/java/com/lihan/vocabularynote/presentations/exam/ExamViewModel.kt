package com.lihan.vocabularynote.presentations.exam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.domain.use_cases.VocabularyNoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val vocabularyNoteUseCases: VocabularyNoteUseCases,
    ) : ViewModel() {

    var state by mutableStateOf(ExamState())

    init {
        viewModelScope.launch {
            state = state.copy(
                examList = vocabularyNoteUseCases.getVocabularyNotes.invoke().last().shuffled(),
                nowNote = state.examList[state.seeingNote]
            )
        }
        getOneNote()
    }

    private fun getOneNote(){
        state = state.copy(
//            nowNote = state.examList[]
        )
    }

    fun onEvent(event: ExamEvent){
        when(event){
            is ExamEvent.NextQuestion->{

            }
            is ExamEvent.PreQuestion->{

            }
        }
    }

}