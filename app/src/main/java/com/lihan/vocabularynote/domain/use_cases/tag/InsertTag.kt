package com.lihan.vocabularynote.domain.use_cases.tag

import com.lihan.vocabularynote.domain.model.Tag
import com.lihan.vocabularynote.domain.repository.TagRepository

class InsertTag (
    private val tagRepository: TagRepository
        ){
    suspend operator fun invoke(tag: Tag){
        tagRepository.insert(tag)
    }
}