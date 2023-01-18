package com.lihan.vocabularynote.feature.tag.presentations.add

import com.lihan.vocabularynote.feature.tag.domain.model.Tag

sealed class TagAddEvent{
    data class InitTag(val tag: Tag) : TagAddEvent()
    data class ColorPicked(val color : Int ) : TagAddEvent()
    data class InputTagName(val name : String) : TagAddEvent()
    object InertTag : TagAddEvent()
}