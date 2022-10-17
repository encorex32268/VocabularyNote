package com.lihan.vocabularynote.data.repository

import com.lihan.vocabularynote.core.util.Resource
import com.lihan.vocabularynote.data.VocabularyNoteDao
import com.lihan.vocabularynote.domain.model.VocabularyNote
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VocabularyNoteRepositoryImpl(
    private val dao : VocabularyNoteDao
)  : VocabularyNoteRepository{

    override suspend fun insert(vocabularyNote: VocabularyNote) {
        dao.insert(
            vocabularyNoteEntity =vocabularyNote.toVocabularyNoteEntity()
        )
    }

    override suspend fun getAllVocabulary(): Flow<Resource<List<VocabularyNote>>> {
      return flow {
          // future online ? temp use flow
          emit(Resource.Loading())
          val data = dao.getAllVocabulary().map { it.toVocabularyNote() }
          emit(Resource.Success(data))
      }
    }

    override suspend fun deleteVocabularyNote(noteId : Int ) {
       dao.deleteVocabularyNote(noteId = noteId)
    }
}