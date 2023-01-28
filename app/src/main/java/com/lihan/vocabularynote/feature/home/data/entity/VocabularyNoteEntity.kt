package com.lihan.vocabularynote.feature.home.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class VocabularyNoteEntity(
    @PrimaryKey(autoGenerate = true) val id : Int?=null,
    val type : Int,
    val word : String,
    val hiraganaOrKatakana : String,
    val roma : String,
    val createDate : Long,
    val explain : String,
    val noteStorageId : Long
)
