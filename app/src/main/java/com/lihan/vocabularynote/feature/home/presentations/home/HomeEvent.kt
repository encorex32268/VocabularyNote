package com.lihan.vocabularynote.feature.home.presentations.home

import androidx.compose.ui.graphics.Color

sealed class HomeEvent{
    object GetAllVocabularyNotes : HomeEvent()
    object GetAllStorage : HomeEvent()
    data class SearchByString(val string : String) : HomeEvent()
    data class ChangeHintVisible(val visible: Boolean) : HomeEvent()
    data class GetNotesByStorageId(val storageId : Int) : HomeEvent()
}
