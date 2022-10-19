package com.lihan.vocabularynote.presentations.add

import androidx.compose.ui.graphics.Color

sealed class InsertEditEvent{
    data class IsEditPage(val id : Int ) : InsertEditEvent()
    object Remove : InsertEditEvent()
    object Save : InsertEditEvent()
    data class TypeColorChanged(val color : Color) : InsertEditEvent()
    data class WordChanged(val word : String) : InsertEditEvent()
    data class HiraganaChanged(val hiragana : String) : InsertEditEvent()
    data class ExplainChanged(val explain : String) : InsertEditEvent()


}