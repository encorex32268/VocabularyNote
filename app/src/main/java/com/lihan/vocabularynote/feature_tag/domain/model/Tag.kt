package com.lihan.vocabularynote.feature_tag.domain.model

data class Tag(
    val id : Int ?=null,
    val name : String,
    val color : Int,
    val createAt : Long
)
