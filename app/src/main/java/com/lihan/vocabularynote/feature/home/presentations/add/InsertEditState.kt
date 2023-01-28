package com.lihan.vocabularynote.feature.home.presentations.add

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.tag.domain.model.Tag

data class InsertEditState(
    val storageId : Int = -1,
    val vocabularyNote: VocabularyNote ?= null,
    val tags : List<Tag> = emptyList()
)
