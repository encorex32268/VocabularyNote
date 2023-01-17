package com.lihan.vocabularynote.feature_tag.util

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.lihan.vocabularynote.feature_tag.domain.model.Tag

class AssetTagType : NavType<Tag>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): Tag? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Tag {
        return Gson().fromJson(value, Tag::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Tag) {
        bundle.putParcelable(key,value)
    }
}