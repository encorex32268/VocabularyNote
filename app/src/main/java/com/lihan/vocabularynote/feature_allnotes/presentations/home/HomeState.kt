package com.lihan.vocabularynote.feature_allnotes.presentations.home

import com.lihan.vocabularynote.feature_allnotes.domain.model.VocabularyNote

data class HomeState(
    val notes : List<VocabularyNote> = emptyList(),
    val searchText : String = "",
    val isHintVisible : Boolean = false
)
