package com.lihan.vocabularynote.feature.home.presentations.home

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
    private val preferences: Preferences
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        onEvent(HomeEvent.GetAllStorage)
        onEvent(HomeEvent.GetAllVocabularyNotes)
        state = state.copy(
            userIcon = preferences.getUserIcon(),
            userName = preferences.getUserName()
        )
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
            is HomeEvent.GetNotesByStorageId -> {
                viewModelScope.launch {
                    vocabularyNoteUseCases.getVocabularyByStorageId(event.storageId).collectLatest {
                        state = state.copy(
                            notes = it
                        )
                    }
                }
            }
            is HomeEvent.SearchByString ->{
                val data = state.notes
                if (event.string.isNotBlank()){
                    state = state.copy(
                        notes = data.filter { note ->
                            note.hiraganaOrKatakana.contains(event.string) ||
                                    note.word.contains(event.string) },
                        searchText = event.string
                    )
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
            is HomeEvent.SaveUserName->{
                state = state.copy(
                    userName = event.name
                )
                preferences.saveUserName(event.name)
            }
            is HomeEvent.SaveUserIcon->{
                val newUserIcon = User.icons.filter { it != event.resId }.random()
                state = state.copy(
                    userIcon = newUserIcon
                )
                preferences.saveUserIcon(newUserIcon)
            }

        }

    }

}



