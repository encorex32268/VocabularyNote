package com.lihan.vocabularynote.feature.storage.presentations.edit

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

sealed class StorageEditEvent{
    data class GetVocabularyByStorageId(val storageId : Int) : StorageEditEvent()
    data class InsertVocabulary(val vocabularyNote: VocabularyNote) : StorageEditEvent()
    data class DeleteVocabulary(val noteId : Int) : StorageEditEvent()
    data class UpdateVocabulary(val vocabularyNote: VocabularyNote) : StorageEditEvent()
    data class UpdateStorage(val storage : Storage) : StorageEditEvent()
}
