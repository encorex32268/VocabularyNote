package com.lihan.vocabularynote.presentations.home

import com.lihan.vocabularynote.domain.model.VocabularyNote

data class HomeState(
    val notes : List<VocabularyNote> = emptyList(),
    val searchText : String = "",
    val isHintVisible : Boolean = false
)
