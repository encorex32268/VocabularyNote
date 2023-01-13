package com.lihan.vocabularynote.feature_allnotes.domain.use_cases

import com.lihan.vocabularynote.feature_allnotes.domain.repository.VocabularyNoteRepository

class DeleteVocabularyNote(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(noteId : Int) {
        vocabularyNoteRepository.deleteVocabularyNote(noteId)
    }
}