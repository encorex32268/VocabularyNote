package com.lihan.vocabularynote.domain.use_cases

import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow

class GetVocabularyByNoteId(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    operator fun invoke(noteId : Int ) : Flow<VocabularyNote> {
        return vocabularyNoteRepository.getVocabularyNote(noteId)
    }
}