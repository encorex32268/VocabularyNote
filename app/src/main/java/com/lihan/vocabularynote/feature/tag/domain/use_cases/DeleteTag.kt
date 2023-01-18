package com.lihan.vocabularynote.feature.tag.domain.use_cases

import com.lihan.vocabularynote.feature.tag.domain.repository.TagRepository

class DeleteTag(
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(tagId : Int){
        tagRepository.deleteTag(tagId)
    }
}