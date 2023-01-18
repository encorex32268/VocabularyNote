package com.lihan.vocabularynote.feature.home.domain.use_cases

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.domain.repository.VocabularyNoteRepository

class GetVocabularyByNoteId(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(noteId : Int ) : VocabularyNote? {
        return vocabularyNoteRepository.getVocabularyNote(noteId)
    }
}