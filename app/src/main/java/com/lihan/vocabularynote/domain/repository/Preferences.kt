package com.lihan.vocabularynote.domain.repository

import androidx.compose.ui.graphics.Color

interface Preferences {

    fun saveUserColorWhenAdd(color : Color)
    fun loadUserColorWhenAdd() : Color


}