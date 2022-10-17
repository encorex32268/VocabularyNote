package com.lihan.vocabularynote.domain.use_cases

import com.lihan.vocabularynote.core.util.Resource
import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow

class GetVocabularyNotes(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {

    suspend  operator fun invoke() : Flow<Resource<List<VocabularyNote>>> {
        return vocabularyNoteRepository.getAllVocabulary()
    }
}