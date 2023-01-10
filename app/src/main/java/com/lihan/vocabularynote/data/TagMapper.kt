package com.lihan.vocabularynote.data

import com.lihan.vocabularynote.data.entity.TagEntity
import com.lihan.vocabularynote.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.domain.model.Tag
import com.lihan.vocabularynote.domain.model.VocabularyNote

fun TagEntity.toTag() : Tag {
    return Tag(
        id = id,
        name = name ,
        color = color,
        createAt = createAt
    )
}

fun Tag.toTagEntity() : TagEntity {
    return TagEntity(
        id = id,
        name = name,
        color = color,
        createAt = createAt
    )
}