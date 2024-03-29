package com.lihan.vocabularynote.feature.tag.domain.use_cases

import com.lihan.vocabularynote.feature.tag.domain.model.Tag
import com.lihan.vocabularynote.feature.tag.domain.repository.TagRepository

class InsertTag (
    private val tagRepository: TagRepository
        ){
    suspend operator fun invoke(tag: Tag){
        tagRepository.insert(tag)
    }
}