package com.lihan.vocabularynote.feature.home.domain.repository


import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import kotlinx.coroutines.flow.Flow

interface VocabularyNoteRepository {
    suspend fun insert(vocabularyNote: VocabularyNote)
    fun getAllVocabulary() : Flow<List<VocabularyNote>>
    suspend fun deleteVocabularyNote(noteId : Int)
    suspend fun deleteVocabularyNoteByStorageId(storageId : Int)
    suspend fun updateVocabularyNote(vocabularyNote : VocabularyNote)
    suspend fun getVocabularyNote(noteId : Int ) : VocabularyNote?
    fun getVocabularyByStorageId(storageId : Int) : Flow<List<VocabularyNote>>
}