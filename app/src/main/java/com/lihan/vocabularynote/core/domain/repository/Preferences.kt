package com.lihan.vocabularynote.core.domain.repository

import androidx.compose.ui.graphics.Color

interface Preferences {

    fun saveUserColorWhenAdd(colorArgb : Int)
    fun loadUserColorWhenAdd() : Color

    fun saveUserName(name : String)
    fun getUserName() : String
    fun saveUserIcon(resId : Int)
    fun getUserIcon() : Int

}