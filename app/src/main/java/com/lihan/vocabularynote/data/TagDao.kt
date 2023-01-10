package com.lihan.vocabularynote.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lihan.vocabularynote.data.entity.TagEntity
import com.lihan.vocabularynote.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.domain.model.Tag
import com.lihan.vocabularynote.domain.model.VocabularyNote
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tagEntity: TagEntity)

    @Query("select * from TagEntity")
    fun getAllTag() : Flow<List<TagEntity>>

    @Query("delete from TagEntity where id = :tagId")
    suspend fun deleteTag(tagId : Int)

}