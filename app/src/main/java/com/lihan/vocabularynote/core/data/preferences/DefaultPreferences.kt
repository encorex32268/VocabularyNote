package com.lihan.vocabularynote.core.data.preferences

import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.lihan.vocabularynote.core.domain.model.User
import com.lihan.vocabularynote.core.domain.repository.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
)  : Preferences {

    override fun saveUserColorWhenAdd(colorArgb: Int) {
        sharedPreferences.edit().putInt(USER_ADD_COLOR,colorArgb).apply()
    }

    override fun loadUserColorWhenAdd(): Int {
        return sharedPreferences.getInt(USER_ADD_COLOR,0)
    }

    override fun saveUserName(name: String) {
        sharedPreferences.edit().putString(USER_NAME,name).apply()
    }

    override fun getUserName(): String {
        return sharedPreferences.getString(USER_NAME,"User")?:"User"
    }

    override fun saveUserIcon(resId: Int) {
        sharedPreferences.edit().putInt(USER_ICON,resId).apply()
    }

    override fun getUserIcon(): Int {
       return sharedPreferences.getInt(USER_ICON, User.icons[0])
    }

    companion object{
        private const val USER_ADD_COLOR = "user_add_color"
        private const val USER_NAME = "user_name"
        private const val USER_ICON = "user_icon"

    }
}