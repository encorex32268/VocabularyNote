package com.lihan.vocabularynote.data.repository

import com.lihan.vocabularynote.data.*
import com.lihan.vocabularynote.domain.model.Tag
import com.lihan.vocabularynote.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagRepositoryImpl(
    private val dao : TagDao
)  : TagRepository{

    override suspend fun insert(tag: Tag) {
        dao.insert(tag.toTagEntity())
    }

    override fun getAllTag(): Flow<List<Tag>> {
       return dao.getAllTag().map {
           it.map {
               it.toTag()
           }
       }
    }

    override suspend fun deleteTag(tagId: Int) {
        dao.deleteTag(tagId)
    }


}