package com.lihan.vocabularynote.domain.use_cases.vocabulary

class VocabularyNoteUseCases(
    val getVocabularyNotes: GetVocabularyNotes,
    val getVocabularyByNoteId: GetVocabularyByNoteId,
    val deleteVocabularyNote: DeleteVocabularyNote,
    val insertEditVocabularyNote: InsertEditVocabularyNote
)