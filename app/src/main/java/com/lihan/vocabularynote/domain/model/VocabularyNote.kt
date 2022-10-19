package com.lihan.vocabularynote.domain.model

import androidx.compose.ui.graphics.Color
import com.lihan.vocabularynote.data.entity.VocabularyNoteEntity

data class VocabularyNote(
    val id : Int ,
    //color agb save
    val type : Int,
    val word : String,
    val hiraganaOrKatakana : String,
    val roma : String,
    val createDate : Long,
    val explain : String
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

    fun toVocabularyNoteEntity(): VocabularyNoteEntity {
        return VocabularyNoteEntity(
            type = type,
            word = word,
            hiraganaOrKatakana = hiraganaOrKatakana,
            roma = roma,
            createDate = createDate,
            explain = explain
        )
    }


}
