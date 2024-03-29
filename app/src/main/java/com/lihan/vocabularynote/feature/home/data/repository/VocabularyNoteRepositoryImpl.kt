package com.lihan.vocabularynote.feature.home.data.repository

import com.lihan.vocabularynote.feature.home.data.VocabularyNoteDao
import com.lihan.vocabularynote.feature.home.data.toVocabularyEntity
import com.lihan.vocabularynote.feature.home.data.toVocabularyNote
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VocabularyNoteRepositoryImpl(
    private val dao : VocabularyNoteDao
)  : VocabularyNoteRepository {

    override suspend fun insert(vocabularyNote: VocabularyNote) {
        dao.insert(vocabularyNoteEntity =vocabularyNote.toVocabularyEntity())
    }

    override fun getAllVocabulary(): Flow<List<VocabularyNote>> {
        return dao.getAllVocabulary().map { it.map {
                note -> note.toVocabularyNote()
        }
      }

    }

    override suspend fun deleteVocabularyNote(noteId : Int ) {
        dao.deleteVocabularyNote(noteId)
    }

    override suspend fun deleteVocabularyNoteByStorageId(storageId: Int) {
        dao.deleteVocabularyNoteByStorageId(storageId)
    }

    override suspend fun updateVocabularyNote(vocabularyNote: VocabularyNote) {
        dao.updateVocabularyNote(vocabularyNote.toVocabularyEntity())
    }

    override suspend fun getVocabularyNote(noteId : Int): VocabularyNote? {
        return dao.getVocabularyNote(noteId = noteId)?.toVocabularyNote()
    }

    override fun getVocabularyByStorageId(storageId: Int): Flow<List<VocabularyNote>> {
        return dao.getVocabularyByStorageId(storageId).map {
            it.map {
                it.toVocabularyNote()
            }
        }
    }

    override fun getVocabularyByText(text: String): Flow<List<VocabularyNote>> {
        return dao.getVocabularyByText(text).map {
            it.map {
                it.toVocabularyNote()
            }
        }
    }
}