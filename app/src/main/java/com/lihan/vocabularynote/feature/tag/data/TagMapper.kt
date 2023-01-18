package com.lihan.vocabularynote.feature.tag.data

import com.lihan.vocabularynote.feature.tag.data.entity.TagEntity
import com.lihan.vocabularynote.feature.tag.domain.model.Tag

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