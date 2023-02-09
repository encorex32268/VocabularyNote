package com.lihan.vocabularynote.feature.home.domain.use_cases

import com.lihan.vocabularynote.feature.home.domain.repository.VocabularyNoteRepository

class DeleteVocabularyNoteByStorageId(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(storageId : Int) {
        vocabularyNoteRepository.deleteVocabularyNoteByStorageId(storageId)
    }
}