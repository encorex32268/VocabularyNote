package com.lihan.vocabularynote.domain.use_cases

import com.lihan.vocabularynote.core.util.Resource
import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetVocabularyNotes(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {

    operator fun invoke() : Flow<List<VocabularyNote>> {
        return vocabularyNoteRepository.getAllVocabulary()
    }
}