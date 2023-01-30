package com.lihan.vocabularynote.feature.storage.presentations.edit

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.presentations.StorageEvent

sealed class StorageEditEvent{
    data class GetVocabularyByStorageId(val storageId : Int) : StorageEditEvent()
    data class InsertVocabulary(val vocabularyNote: VocabularyNote) : StorageEditEvent()
    data class DeleteVocabulary(val noteId : Int) : StorageEditEvent()
    data class UpdateVocabulary(val vocabularyNote: VocabularyNote) : StorageEditEvent()
    object UpdateStorage : StorageEditEvent()
    data class InitStorage(val storage : Storage) : StorageEditEvent()
    data class ChangeStorageName(val text : String) : StorageEditEvent()
    data class ChangeStorageDescription(val description : String) : StorageEditEvent()
    data class DeleteStorage(val storageId : Int) : StorageEditEvent()

}
