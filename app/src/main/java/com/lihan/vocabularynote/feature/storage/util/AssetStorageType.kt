package com.lihan.vocabularynote.feature.storage.util

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

class AssetStorageType : NavType<Storage>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): Storage? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Storage {
        return Gson().fromJson(value,Storage::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Storage) {
        bundle.putParcelable(key,value)
    }

}