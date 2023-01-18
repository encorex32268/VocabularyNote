package com.lihan.vocabularynote.feature.home.presentations.home

import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote

data class HomeState(
    val notes : List<VocabularyNote> = emptyList(),
    val searchText : String = "",
    val isHintVisible : Boolean = false
)
