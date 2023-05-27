package com.lihan.vocabularynote.feature.tag.presentations.add

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb


data class TagAddState(
    val id : Int?=null,
    val name : String = "",
    val color : Int = Color.Blue.toArgb(),
    val createAt : Long = 0
)