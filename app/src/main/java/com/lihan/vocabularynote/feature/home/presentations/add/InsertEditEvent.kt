package com.lihan.vocabularynote.feature.home.presentations.add

import androidx.compose.ui.graphics.Color

sealed class InsertEditEvent{
    data class IsEditPage(val id : Int ) : InsertEditEvent()
    data class Remove(val removeId : Int ) : InsertEditEvent()
    object Save : InsertEditEvent()
    data class TypeColorChanged(val colorArgb : Int) : InsertEditEvent()
    data class WordChanged(val word : String) : InsertEditEvent()
    data class HiraganaChanged(val hiragana : String) : InsertEditEvent()
    data class ExplainChanged(val explain : String) : InsertEditEvent()
    data class IsAddPage(val storageId : Int) : InsertEditEvent()


}