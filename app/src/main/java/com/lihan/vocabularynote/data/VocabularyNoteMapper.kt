package com.lihan.vocabularynote.data

import com.lihan.vocabularynote.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.domain.model.VocabularyNote

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