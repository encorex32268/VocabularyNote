package com.lihan.vocabularynote.feature_allnotes.domain.repository


import com.lihan.vocabularynote.feature_allnotes.domain.model.VocabularyNote
import kotlinx.coroutines.flow.Flow

interface VocabularyNoteRepository {

    suspend fun insert(vocabularyNote: VocabularyNote)

    fun getAllVocabulary() : Flow<List<VocabularyNote>>

    suspend fun deleteVocabularyNote(noteId : Int)

    suspend fun getVocabularyNote(noteId : Int ) : VocabularyNote?
}