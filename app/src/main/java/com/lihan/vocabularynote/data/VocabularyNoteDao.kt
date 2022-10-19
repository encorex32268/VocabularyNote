package com.lihan.vocabularynote.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lihan.vocabularynote.data.entity.VocabularyNoteEntity

@Dao
interface VocabularyNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vocabularyNoteEntity: VocabularyNoteEntity)

    @Query("select * from VocabularyNoteEntity")
    suspend fun getAllVocabulary() : List<VocabularyNoteEntity>

    @Query("Delete from VocabularyNoteEntity where id =:noteId")
    suspend fun deleteVocabularyNote(noteId : Int)

    @Query("select * from VocabularyNoteEntity where id =:noteId")
    suspend fun getVocabularyNote(noteId: Int) : VocabularyNoteEntity
}