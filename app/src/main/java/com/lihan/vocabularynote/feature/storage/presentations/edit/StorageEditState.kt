package com.lihan.vocabularynote.feature.storage.presentations.edit

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote

data class StorageEditState(
    val items : List<VocabularyNote> = emptyList()
)
