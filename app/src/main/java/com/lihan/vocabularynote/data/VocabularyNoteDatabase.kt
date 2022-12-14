package com.lihan.vocabularynote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lihan.vocabularynote.data.entity.TagEntity
import com.lihan.vocabularynote.data.entity.VocabularyNoteEntity

@Database(
    entities = [
        VocabularyNoteEntity::class,
        TagEntity::class
               ],
    version = 1,
    exportSchema = false
)
abstract class VocabularyNoteDatabase : RoomDatabase() {
    abstract val vocabularyNoteDao : VocabularyNoteDao
    abstract val tagDao : TagDao
}