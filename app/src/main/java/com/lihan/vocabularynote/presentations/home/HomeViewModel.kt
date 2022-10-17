package com.lihan.vocabularynote.presentations.home

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getVocabularyNotes: GetVocabularyNotes
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set
    init {
        state = state.copy(
            notes = arrayListOf(
                VocabularyNote(
                    0,"T","使う","つかう","tsukau",1000L,"使用"
                ),
                VocabularyNote(
                    0,"T","使う","つかう","tsukau",1000L,"使用"
                ),
                VocabularyNote(
                    0,"T","使う","つかう","tsukau",1000L,"使用"
                ),
                VocabularyNote(
                    0,"T","使う","つかう","tsukau",1000L,"使用"
                ),
            )
        )
    }

    fun onEvent(event : HomeEvent){
        when(event){
            HomeEvent.GetNotes->{
                viewModelScope.launch {
                    getVocabularyNotes.invoke().onEach {
                        when(it){
                            is Resource.Success->{
                                state = state.copy(
                                    notes = it.data?: emptyList()
                                )
                            }
                            else -> {
                                state = state.copy(
                                    notes = emptyList()
                                )
                            }
                        }
                    }

                }

            }
        }
    }




}