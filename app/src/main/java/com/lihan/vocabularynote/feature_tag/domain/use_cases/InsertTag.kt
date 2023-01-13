package com.lihan.vocabularynote.feature_tag.domain.use_cases

import com.lihan.vocabularynote.feature_tag.domain.model.Tag
import com.lihan.vocabularynote.feature_tag.domain.repository.TagRepository

class InsertTag (
    private val tagRepository: TagRepository
        ){
    suspend operator fun invoke(tag: Tag){
        tagRepository.insert(tag)
    }
}