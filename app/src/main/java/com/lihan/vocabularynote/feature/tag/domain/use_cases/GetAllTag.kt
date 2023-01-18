package com.lihan.vocabularynote.feature.tag.domain.use_cases

import com.lihan.vocabularynote.feature.tag.domain.model.Tag
import com.lihan.vocabularynote.feature.tag.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow

class GetAllTag(
    private val tagRepository: TagRepository
) {
    operator fun invoke() : Flow<List<Tag>>{
        return tagRepository.getAllTag()
    }
}