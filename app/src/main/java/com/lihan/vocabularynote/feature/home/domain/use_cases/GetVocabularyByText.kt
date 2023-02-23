package com.lihan.vocabularynote.feature.home.domain.use_cases

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow

class GetVocabularyByText(
    private val vocabularyNoteRepository: VocabularyNoteRepository
)  {
    operator fun invoke(text : String) : Flow<List<VocabularyNote>> {
        return vocabularyNoteRepository.getVocabularyByText(text)
    }

}