package com.lihan.vocabularynote.feature.home.presentations.home

import com.lihan.vocabularynote.core.domain.model.User
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

data class HomeState(
    val notes : List<VocabularyNote> = emptyList(),
    val storages : List<Storage> = emptyList(),
    val spinnerSelected : String = "",
    val searchText : String = "",
    val isHintVisible : Boolean = false,
    val userName : String = "",
    val userIcon : Int = User.icons[0],
)
