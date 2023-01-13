package com.lihan.vocabularynote.feature_allnotes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lihan.vocabularynote.feature_allnotes.data.entity.VocabularyNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vocabularyNoteEntity: VocabularyNoteEntity)

    @Query("select * from VocabularyNoteEntity")
    fun getAllVocabulary() : Flow<List<VocabularyNoteEntity>>

    @Query("delete from VocabularyNoteEntity where id = :noteId")
    suspend fun deleteVocabularyNote(noteId : Int)

    @Query("select * from VocabularyNoteEntity where id = :noteId")
    suspend fun getVocabularyNote(noteId: Int) : VocabularyNoteEntity?
}