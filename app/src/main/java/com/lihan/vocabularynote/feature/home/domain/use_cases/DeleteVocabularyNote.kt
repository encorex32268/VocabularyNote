package com.lihan.vocabularynote.feature.home.domain.use_cases

import com.lihan.vocabularynote.feature.home.domain.repository.VocabularyNoteRepository

class DeleteVocabularyNote(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(noteId : Int) {
        vocabularyNoteRepository.deleteVocabularyNote(noteId)
    }
}