package com.lihan.vocabularynote.feature_allnotes.domain.use_cases

import com.lihan.vocabularynote.feature_allnotes.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature_allnotes.domain.repository.VocabularyNoteRepository

class GetVocabularyByNoteId(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(noteId : Int ) : VocabularyNote? {
        return vocabularyNoteRepository.getVocabularyNote(noteId)
    }
}