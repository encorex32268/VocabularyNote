package com.lihan.vocabularynote.feature.home.domain.use_cases

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.domain.repository.VocabularyNoteRepository

class InsertEditVocabularyNote(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(vocabularyNote: VocabularyNote){
        vocabularyNoteRepository.insert(vocabularyNote)
    }
}