package com.lihan.vocabularynote.presentations.exam

import com.lihan.vocabularynote.domain.model.VocabularyNote

data class ExamState(
    val examList : List<VocabularyNote> = emptyList(),
    val tempNote : VocabularyNote?= null,
    val nowNote : VocabularyNote?=null,
    val seeingNote : Int = 0
)
