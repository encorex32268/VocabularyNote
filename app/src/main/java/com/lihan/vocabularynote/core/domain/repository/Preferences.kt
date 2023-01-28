package com.lihan.vocabularynote.core.domain.repository

import androidx.compose.ui.graphics.Color

interface Preferences {

    fun saveUserColorWhenAdd(colorArgb : Int)
    fun loadUserColorWhenAdd() : Color


}