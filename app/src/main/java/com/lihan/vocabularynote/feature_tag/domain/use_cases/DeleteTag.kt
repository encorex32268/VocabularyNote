package com.lihan.vocabularynote.feature_tag.domain.use_cases

import com.lihan.vocabularynote.feature_tag.domain.repository.TagRepository

class DeleteTag(
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(tagId : Int){
        tagRepository.deleteTag(tagId)
    }
}