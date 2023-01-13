package com.lihan.vocabularynote.feature_allnotes.domain.use_cases

import com.lihan.vocabularynote.feature_allnotes.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature_allnotes.domain.repository.VocabularyNoteRepository

class InsertEditVocabularyNote(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(vocabularyNote: VocabularyNote){
        vocabularyNoteRepository.insert(vocabularyNote)
    }
}