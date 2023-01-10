package com.lihan.vocabularynote.domain.use_cases.vocabulary

import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow

class GetVocabularyByNoteId(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(noteId : Int ) : VocabularyNote? {
        return vocabularyNoteRepository.getVocabularyNote(noteId)
    }
}