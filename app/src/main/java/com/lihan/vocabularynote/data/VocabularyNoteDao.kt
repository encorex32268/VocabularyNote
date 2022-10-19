package com.lihan.vocabularynote.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lihan.vocabularynote.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.domain.model.VocabularyNote
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vocabularyNoteEntity: VocabularyNoteEntity)

    @Query("select * from VocabularyNoteEntity")
    fun getAllVocabulary() : Flow<List<VocabularyNoteEntity>>

    @Delete
    suspend fun deleteVocabularyNote(vocabularyNoteEntity: VocabularyNoteEntity)

    @Query("select * from VocabularyNoteEntity where id =:noteId")
    fun getVocabularyNote(noteId: Int) : Flow<VocabularyNoteEntity>
}