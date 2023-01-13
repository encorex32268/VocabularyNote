package com.lihan.vocabularynote.feature_tag.presentations

import com.lihan.vocabularynote.feature_tag.domain.model.Tag

data class TagState(
    val tags : List<Tag> = emptyList()
)