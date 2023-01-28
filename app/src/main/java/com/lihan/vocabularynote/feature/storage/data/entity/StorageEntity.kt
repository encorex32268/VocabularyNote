package com.lihan.vocabularynote.feature.storage.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lihan.vocabularynote.feature.home.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote

@Entity
data class StorageEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?=null,
    val storageId : Int,
    val name : String,
    val description : String ,
    val createAt : Long
)