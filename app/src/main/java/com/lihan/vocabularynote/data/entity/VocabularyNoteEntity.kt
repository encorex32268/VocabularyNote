package com.lihan.vocabularynote.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lihan.vocabularynote.domain.model.VocabularyNote


@Entity
data class VocabularyNoteEntity(
    @PrimaryKey
    val id : Int?=null,
    val type : Int,
    val word : String,
    val hiraganaOrKatakana : String,
    val roma : String,
    val createDate : Long,
    val explain : String
)
