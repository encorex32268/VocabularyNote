package com.lihan.vocabularynote.domain.repository


import com.lihan.vocabularynote.core.util.Resource
import com.lihan.vocabularynote.domain.model.VocabularyNote
import kotlinx.coroutines.flow.Flow

interface VocabularyNoteRepository {

    suspend fun insert(vocabularyNote: VocabularyNote)

    fun getAllVocabulary() : Flow<List<VocabularyNote>>

    suspend fun deleteVocabularyNote(vocabularyNote : VocabularyNote)

    fun getVocabularyNote(noteId : Int ) : Flow<VocabularyNote>
}