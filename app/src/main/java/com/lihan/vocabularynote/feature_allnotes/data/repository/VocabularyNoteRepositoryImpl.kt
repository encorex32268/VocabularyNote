package com.lihan.vocabularynote.feature_allnotes.data.repository

import com.lihan.vocabularynote.feature_allnotes.data.VocabularyNoteDao
import com.lihan.vocabularynote.feature_allnotes.data.toVocabularyEntity
import com.lihan.vocabularynote.feature_allnotes.data.toVocabularyNote
import com.lihan.vocabularynote.feature_allnotes.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature_allnotes.domain.repository.VocabularyNoteRepository
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

    override suspend fun getVocabularyNote(noteId : Int): VocabularyNote? {
        return dao.getVocabularyNote(noteId = noteId)?.toVocabularyNote()
    }
}