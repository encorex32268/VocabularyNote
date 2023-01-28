package com.lihan.vocabularynote.feature.home.data

import com.lihan.vocabularynote.feature.home.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote

fun VocabularyNoteEntity.toVocabularyNote() : VocabularyNote {
    return VocabularyNote(
        id = id,
        type = type,
        word = word ,
        hiraganaOrKatakana = hiraganaOrKatakana,
        roma = roma,
        createDate = createDate,
        explain = explain,
        storageId = storageId

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
        explain = explain,
        storageId = storageId
    )
}