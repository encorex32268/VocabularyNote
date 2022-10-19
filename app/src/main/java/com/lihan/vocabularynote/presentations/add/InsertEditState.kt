package com.lihan.vocabularynote.presentations.add

import androidx.compose.ui.graphics.toArgb
import com.lihan.vocabularynote.domain.model.VocabularyNote

data class InsertEditState(
    val typeColor : Int  = VocabularyNote.typeColors.random().toArgb(),
    val word : String = "" ,
    val hiragana  : String ="",
    val roma  : String = "",
    val createDate : Long = System.currentTimeMillis(),
    val explain : String = ""
)
