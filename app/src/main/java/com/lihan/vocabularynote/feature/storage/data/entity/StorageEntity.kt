package com.lihan.vocabularynote.feature.storage.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StorageEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?=null,
    val storageId : Int,
    val name : String,
    val description : String ,
    val createAt : Long,
)