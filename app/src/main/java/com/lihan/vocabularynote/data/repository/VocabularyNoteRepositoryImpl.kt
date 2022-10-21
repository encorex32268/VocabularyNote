package com.lihan.vocabularynote.data.repository

import android.util.Log
import com.lihan.vocabularynote.core.util.Resource
import com.lihan.vocabularynote.data.VocabularyNoteDao
import com.lihan.vocabularynote.data.toVocabularyEntity
import com.lihan.vocabularynote.data.toVocabularyNote
import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class VocabularyNoteRepositoryImpl(
    private val dao : VocabularyNoteDao
)  : VocabularyNoteRepository{

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