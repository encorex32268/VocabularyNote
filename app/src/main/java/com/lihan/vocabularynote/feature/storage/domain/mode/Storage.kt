package com.lihan.vocabularynote.feature.storage.domain.mode

data class Storage(
    val id : Int ?=null,
    val storageId : Int,
    val name : String,
    val description : String ,
    val createAt : Long,
)