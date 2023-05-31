package com.lihan.vocabularynote.feature.home.presentations.home

import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

sealed class HomeEvent{
    object GetAllVocabularyNotes : HomeEvent()
    object GetAllStorage : HomeEvent()
    data class SearchByString(val string : String) : HomeEvent()
    data class ChangeHintVisible(val visible: Boolean) : HomeEvent()
    data class GetNotesByStorageId(val storageId : Int) : HomeEvent()
    data class SaveUserName(val name : String) : HomeEvent()
    data class SaveUserIcon(val resId : Int) : HomeEvent()
    data class SpinnerStorageChanged(val storage: Storage?) : HomeEvent()
}
