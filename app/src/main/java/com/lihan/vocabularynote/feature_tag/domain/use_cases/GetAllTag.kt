package com.lihan.vocabularynote.feature_tag.domain.use_cases

import com.lihan.vocabularynote.feature_tag.domain.model.Tag
import com.lihan.vocabularynote.feature_tag.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow

class GetAllTag(
    private val tagRepository: TagRepository
) {
    operator fun invoke() : Flow<List<Tag>>{
        return tagRepository.getAllTag()
    }
}