package com.lihan.vocabularynote.feature.tag.presentations

import com.lihan.vocabularynote.feature.tag.domain.model.Tag

sealed class TagEvent{
    object GetTags : TagEvent()
    data class InertTag(
        val tag : Tag
    ) : TagEvent()

    data class DeleteTag(
        val tag : Tag?
    ) : TagEvent()
}