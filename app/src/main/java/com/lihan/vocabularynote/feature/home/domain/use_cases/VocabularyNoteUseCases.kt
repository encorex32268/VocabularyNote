package com.lihan.vocabularynote.feature.home.domain.use_cases

class VocabularyNoteUseCases(
    val getVocabularyNotes: GetVocabularyNotes,
    val getVocabularyByText: GetVocabularyByText,
    val getVocabularyByNoteId: GetVocabularyByNoteId,
    val deleteVocabularyNote: DeleteVocabularyNote,
    val insertEditVocabularyNote: InsertEditVocabularyNote,
    val getVocabularyByStorageId: GetVocabularyByStorageId,
    val updateVocabularyNote: UpdateVocabularyNote,
    val deleteVocabularyNoteByStorageId: DeleteVocabularyNoteByStorageId
)