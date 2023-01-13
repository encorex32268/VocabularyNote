package com.lihan.vocabularynote.feature_allnotes.data

import com.lihan.vocabularynote.feature_allnotes.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.feature_allnotes.domain.model.VocabularyNote

fun VocabularyNoteEntity.toVocabularyNote() : VocabularyNote {
    return VocabularyNote(
        id = id,
        type = type,
        word = word ,
        hiraganaOrKatakana = hiraganaOrKatakana,
        roma = roma,
        createDate = createDate,
        explain = explain
    )
}

fun VocabularyNote.toVocabularyEntity() : VocabularyNoteEntity {
    return VocabularyNoteEntity(
        id = id,
        type = type,
        word = word ,
        hiraganaOrKatakana = hiraganaOrKatakana,
        roma = roma,
        createDate = createDate,
        explain = explain
    )
}