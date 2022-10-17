package com.lihan.vocabularynote.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lihan.vocabularynote.domain.model.VocabularyNote


@Entity
data class VocabularyNoteEntity(
    @PrimaryKey(autoGenerate = true) val id : Int?=null,
    val type : String,
    val word : String,
    val hiraganaOrKatakana : String,
    val roma : String,
    val createDate : Long,
    val explain : String
){

    fun toVocabularyNote() : VocabularyNote{
        return VocabularyNote(
            id = id?:0,
            type = type,
            word = word,
            hiraganaOrKatakana = hiraganaOrKatakana,
            roma =roma,
            createDate = createDate,
            explain = explain

        )
    }
}
