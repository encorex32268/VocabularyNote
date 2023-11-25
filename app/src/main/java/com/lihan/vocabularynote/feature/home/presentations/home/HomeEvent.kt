package com.lihan.vocabularynote.feature.home.presentations.home

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

sealed class HomeEvent{
    object GetAllVocabularyNotes : HomeEvent()
    object GetAllStorage : HomeEvent()
    object GetAllTags: HomeEvent()
    data class SearchByString(val string : String) : HomeEvent()
    data class ChangeHintVisible(val visible: Boolean) : HomeEvent()
    data class GetNotesByStorageId(val storageId : Int) : HomeEvent()

    data class InsertVocabulary(val vocabularyNote: VocabularyNote) : HomeEvent()
    data class DeleteVocabulary(val noteId : Int) : HomeEvent()
}
