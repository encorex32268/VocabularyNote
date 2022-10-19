package com.lihan.vocabularynote.domain.use_cases

import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository

class DeleteVocabularyNote(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(vocabularyNote: VocabularyNote) {
        vocabularyNoteRepository.deleteVocabularyNote(vocabularyNote)
    }
}