package com.lihan.vocabularynote.presentations.tag

import com.lihan.vocabularynote.domain.model.Tag

data class TagState(
    val tags : List<Tag> = emptyList()
)