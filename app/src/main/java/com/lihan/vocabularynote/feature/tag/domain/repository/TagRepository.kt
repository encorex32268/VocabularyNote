package com.lihan.vocabularynote.feature.tag.domain.repository


import com.lihan.vocabularynote.feature.tag.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {

    suspend fun insert(tag : Tag)

    fun getAllTag() : Flow<List<Tag>>

    suspend fun deleteTag(tagId : Int)

}