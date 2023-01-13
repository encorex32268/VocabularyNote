package com.lihan.vocabularynote.feature_allnotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lihan.vocabularynote.feature_tag.data.entity.TagEntity
import com.lihan.vocabularynote.feature_allnotes.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.feature_tag.data.TagDao

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