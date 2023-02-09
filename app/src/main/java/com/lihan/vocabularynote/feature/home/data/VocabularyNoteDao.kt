package com.lihan.vocabularynote.feature.home.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lihan.vocabularynote.feature.home.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyNoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vocabularyNoteEntity: VocabularyNoteEntity)

    @Query("select * from VocabularyNoteEntity")
    fun getAllVocabulary() : Flow<List<VocabularyNoteEntity>>

    @Query("delete from VocabularyNoteEntity where id = :noteId")
    suspend fun deleteVocabularyNote(noteId : Int)

    @Query("delete from VocabularyNoteEntity where storageId = :storageId")
    suspend fun deleteVocabularyNoteByStorageId(storageId : Int)

    @Query("select * from VocabularyNoteEntity where id = :noteId")
    suspend fun getVocabularyNote(noteId: Int) : VocabularyNoteEntity?

    @Query("select * from VocabularyNoteEntity where storageId = :storageId")
    fun getVocabularyByStorageId(storageId : Int) : Flow<List<VocabularyNoteEntity>>

    @Update
    suspend fun updateVocabularyNote(vocabularyNote: VocabularyNoteEntity)
}