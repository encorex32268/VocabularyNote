package com.lihan.vocabularynote.feature.storage.presentations.edit

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

data class StorageEditState(
    val storage : Storage  = Storage(),
    val items : List<VocabularyNote> = emptyList()
)
