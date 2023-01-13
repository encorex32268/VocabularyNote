package com.lihan.vocabularynote.core.data.preferences

import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.lihan.vocabularynote.core.domain.repository.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
)  : Preferences {
    override fun saveUserColorWhenAdd(color: Color) {
        sharedPreferences.edit().putInt(USER_ADD_COLOR,color.toArgb()).apply()
    }

    override fun loadUserColorWhenAdd(): Color {
        return Color(sharedPreferences.getInt(USER_ADD_COLOR,Color(252,46,4).toArgb()))
    }
    companion object{
        private const val USER_ADD_COLOR = "user_add_color"

    }
}