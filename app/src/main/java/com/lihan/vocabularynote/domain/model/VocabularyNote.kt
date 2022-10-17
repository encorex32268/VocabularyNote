package com.lihan.vocabularynote.domain.model

import com.lihan.vocabularynote.data.entity.VocabularyNoteEntity

data class VocabularyNote(
    val id : Int ,
    val type : String,
    val word : String,
    val hiraganaOrKatakana : String,
    val roma : String,
    val createDate : Long,
    val explain : String
) {
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
