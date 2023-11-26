package com.lihan.vocabularynote.feature.home.data.repository

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeVocabularyNoteRepository : VocabularyNoteRepository {

    private val vocabularyNotes = mutableListOf<VocabularyNote>()

    override suspend fun insert(vocabularyNote: VocabularyNote) {
        vocabularyNotes.add(vocabularyNote)
    }

    override fun getAllVocabulary(): Flow<List<VocabularyNote>> {
        return flow { emit(vocabularyNotes) }
    }

    override suspend fun deleteVocabularyNote(noteId: Int) {
        vocabularyNotes.removeIf { it.id == noteId }
    }

    override suspend fun deleteVocabularyNoteByStorageId(storageId: Int) {
        vocabularyNotes.removeIf { it.storageId == storageId }
    }

    override suspend fun updateVocabularyNote(vocabularyNote: VocabularyNote) {
        val tempId = vocabularyNote.id
        vocabularyNotes.removeIf { it.id == vocabularyNote.id }
        vocabularyNotes.add(vocabularyNote.copy(id = tempId))
    }

    override suspend fun getVocabularyNote(noteId: Int): VocabularyNote? {
        return vocabularyNotes.filter { it.id == noteId }[0]
    }

    override fun getVocabularyByStorageId(storageId: Int): Flow<List<VocabularyNote>> {
        return flow { emit(vocabularyNotes.filter { it.storageId == storageId }) }
    }

    override fun getVocabularyByText(text: String): Flow<List<VocabularyNote>> {
        return flow {
            emit(
                vocabularyNotes.filter {
                    it.hiraganaOrKatakana == text
                }
            )
        }
    }
}