package com.lihan.vocabularynote.feature.tag.presentations

import com.lihan.vocabularynote.feature.tag.domain.model.Tag


sealed class TagEvent{
    object GetTags : TagEvent()
}