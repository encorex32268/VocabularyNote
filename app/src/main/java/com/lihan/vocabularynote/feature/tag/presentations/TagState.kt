package com.lihan.vocabularynote.feature.tag.presentations

import com.lihan.vocabularynote.feature.tag.domain.model.Tag

data class TagState(
    val tags : List<Tag> = emptyList()
)