package com.lihan.vocabularynote.domain.repository


import com.lihan.vocabularynote.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    suspend fun insert(tag : Tag)

    fun getAllTag() : Flow<List<Tag>>

    suspend fun deleteTag(tagId : Int)

}