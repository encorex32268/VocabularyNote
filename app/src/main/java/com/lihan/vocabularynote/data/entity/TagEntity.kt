package com.lihan.vocabularynote.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagEntity(
    @PrimaryKey
    val id : Int ?=null,
    val name : String ,
    val color : Int,
    val createAt : Long
)
