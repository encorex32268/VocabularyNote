package com.lihan.vocabularynote.domain.use_cases.vocabulary

import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository

class InsertEditVocabularyNote(
    private val vocabularyNoteRepository: VocabularyNoteRepository
) {
    suspend operator fun invoke(vocabularyNote: VocabularyNote){
        vocabularyNoteRepository.insert(vocabularyNote)
    }
}