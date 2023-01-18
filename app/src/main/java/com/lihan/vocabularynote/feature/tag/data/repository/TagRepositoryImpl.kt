package com.lihan.vocabularynote.feature.tag.data.repository

import com.lihan.vocabularynote.feature.tag.domain.model.Tag
import com.lihan.vocabularynote.feature.tag.domain.repository.TagRepository
import com.lihan.vocabularynote.feature.tag.data.TagDao
import com.lihan.vocabularynote.feature.tag.data.toTag
import com.lihan.vocabularynote.feature.tag.data.toTagEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagRepositoryImpl(
    private val dao : TagDao
)  : TagRepository {

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