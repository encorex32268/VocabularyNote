package com.lihan.vocabularynote.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.lihan.vocabularynote.feature.home.data.VocabularyNoteDatabase
import com.lihan.vocabularynote.core.data.preferences.DefaultPreferences
import com.lihan.vocabularynote.feature.tag.data.repository.TagRepositoryImpl
import com.lihan.vocabularynote.feature.home.data.repository.VocabularyNoteRepositoryImpl
import com.lihan.vocabularynote.core.domain.repository.Preferences
import com.lihan.vocabularynote.feature.tag.domain.repository.TagRepository
import com.lihan.vocabularynote.feature.home.domain.repository.VocabularyNoteRepository
import com.lihan.vocabularynote.feature.home.domain.use_cases.*
import com.lihan.vocabularynote.feature.storage.data.repository.StorageRepositoryImpl
import com.lihan.vocabularynote.feature.storage.domain.repository.StorageRepository
import com.lihan.vocabularynote.feature.storage.domain.use_cases.*
import com.lihan.vocabularynote.feature.tag.domain.use_cases.DeleteTag
import com.lihan.vocabularynote.feature.tag.domain.use_cases.GetAllTag
import com.lihan.vocabularynote.feature.tag.domain.use_cases.InsertTag
import com.lihan.vocabularynote.feature.tag.domain.use_cases.TagUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesVocabularyNoteDatabase(
        @ApplicationContext context : Context
    ) : VocabularyNoteDatabase {
        return Room.databaseBuilder(
            context,
            VocabularyNoteDatabase::class.java,
            "vocabularyNote_db"
        ).build()
    }


    @Provides
    @Singleton
    fun providesVocabularyNoteRepository(db : VocabularyNoteDatabase) : VocabularyNoteRepository {
        return VocabularyNoteRepositoryImpl(db.vocabularyNoteDao)
    }


    @Provides
    @Singleton
    fun providesVocabularyNoteUseCases(vocabularyNoteRepository: VocabularyNoteRepository) : VocabularyNoteUseCases {
        return VocabularyNoteUseCases(
            GetVocabularyNotes(vocabularyNoteRepository = vocabularyNoteRepository),
            GetVocabularyByNoteId(vocabularyNoteRepository = vocabularyNoteRepository),
            DeleteVocabularyNote(vocabularyNoteRepository = vocabularyNoteRepository),
            InsertEditVocabularyNote(vocabularyNoteRepository = vocabularyNoteRepository),
            GetVocabularyByStorageId(vocabularyNoteRepository = vocabularyNoteRepository),
            UpdateVocabularyNote(vocabularyNoteRepository = vocabularyNoteRepository),
            DeleteVocabularyNoteByStorageId(vocabularyNoteRepository = vocabularyNoteRepository )
        )
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context : Context) : SharedPreferences{
        return context.getSharedPreferences("user_data",Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesPreferences(sharedPreferences: SharedPreferences) : Preferences {
        return DefaultPreferences(sharedPreferences)
    }


    @Provides
    @Singleton
    fun providesTagRepository(db : VocabularyNoteDatabase) : TagRepository {
        return TagRepositoryImpl(db.tagDao)
    }

    @Provides
    @Singleton
    fun providesTagUseCases(tagRepository: TagRepository) : TagUseCases {
        return TagUseCases(
            deleteTag = DeleteTag(tagRepository = tagRepository),
            insertTag = InsertTag(tagRepository = tagRepository),
            getAllTag = GetAllTag(tagRepository = tagRepository)
        )
    }


    @Provides
    @Singleton
    fun providesStorageRepository(db : VocabularyNoteDatabase) : StorageRepository {
        return StorageRepositoryImpl(db.storageDao)
    }

    @Provides
    @Singleton
    fun providesStorageUseCases(
        storageRepository: StorageRepository,
    ) : StorageUseCases {
        return StorageUseCases(
            deleteStorage = DeleteStorage(storageRepository),
            getStorageById = GetStorageById(storageRepository),
            getStorages = GetStorages(storageRepository),
            insertStorage = InsertStorage(storageRepository),
            updateStorage = UpdateStorage(storageRepository)
        )
    }

}