package com.lihan.vocabularynote.feature_tag.presentations

import com.lihan.vocabularynote.feature_tag.domain.model.Tag


sealed class TagEvent{
    object GetTags : TagEvent()
}