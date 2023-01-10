package com.lihan.vocabularynote.domain.use_cases.tag

import com.lihan.vocabularynote.domain.repository.TagRepository

class DeleteTag(
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(tagId : Int){
        tagRepository.deleteTag(tagId)
    }
}