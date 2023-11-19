package com.lihan.vocabularynote.feature.storage.presentations

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

data class StorageState(
    val searchText : String = "",
    val items : List<Storage> = emptyList(),
    val isHintVisible : Boolean = false,
    val notes : List<VocabularyNote> = emptyList()

)
