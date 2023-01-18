package com.lihan.vocabularynote.feature.home.domain.use_cases

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetVocabularyNotes(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {

    operator fun invoke() : Flow<List<VocabularyNote>> {
        return vocabularyNoteRepository.getAllVocabulary().map {
            it.sortedByDescending { note ->
                note.createDate
            }
        }
    }
}