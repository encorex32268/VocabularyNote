package com.lihan.vocabularynote.feature.home.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lihan.vocabularynote.feature.tag.data.entity.TagEntity
import com.lihan.vocabularynote.feature.home.data.entity.VocabularyNoteEntity
import com.lihan.vocabularynote.feature.storage.data.StorageDao
import com.lihan.vocabularynote.feature.storage.data.entity.StorageEntity
import com.lihan.vocabularynote.feature.tag.data.TagDao

@Database(
    entities = [
        VocabularyNoteEntity::class,
        TagEntity::class,
        StorageEntity::class
               ],
    version = 1,
    exportSchema = false
)
abstract class VocabularyNoteDatabase : RoomDatabase() {
    abstract val vocabularyNoteDao : VocabularyNoteDao
    abstract val tagDao : TagDao
    abstract val storageDao : StorageDao
}