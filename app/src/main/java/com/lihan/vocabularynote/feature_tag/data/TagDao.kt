package com.lihan.vocabularynote.feature_tag.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lihan.vocabularynote.feature_tag.data.entity.TagEntity
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