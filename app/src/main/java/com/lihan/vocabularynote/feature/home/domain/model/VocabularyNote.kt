package com.lihan.vocabularynote.feature.home.domain.model

import androidx.compose.ui.graphics.Color

data class VocabularyNote(
    val id : Int?=null ,
    //color agb save
    val type : Int,
    val word : String,
    val hiraganaOrKatakana : String,
    val roma : String,
    val createDate : Long,
    val explain : String,
    val noteStorageId : Long
) {
    companion object{
        val typeColors = listOf<Color>(
            Color(252,46,4),
            Color(250,244,5),
            Color(7,173,239),
            Color(4,252,4),
            Color(164,36,244),
            Color(148,76,4),
            Color(248,116,8),

        )
    }
}
