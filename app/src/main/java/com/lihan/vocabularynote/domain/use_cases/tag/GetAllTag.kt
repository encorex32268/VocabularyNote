package com.lihan.vocabularynote.domain.use_cases.tag

import com.lihan.vocabularynote.domain.model.Tag
import com.lihan.vocabularynote.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow

class GetAllTag(
    private val tagRepository: TagRepository
) {
    operator fun invoke() : Flow<List<Tag>>{
        return tagRepository.getAllTag()
    }
}