package com.lihan.vocabularynote.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.lihan.vocabularynote.feature_allnotes.data.VocabularyNoteDatabase
import com.lihan.vocabularynote.core.data.preferences.DefaultPreferences
import com.lihan.vocabularynote.feature_tag.data.repository.TagRepositoryImpl
import com.lihan.vocabularynote.feature_allnotes.data.repository.VocabularyNoteRepositoryImpl
import com.lihan.vocabularynote.feature_allnotes.domain.use_cases.*
import com.lihan.vocabularynote.core.domain.repository.Preferences
import com.lihan.vocabularynote.feature_tag.domain.repository.TagRepository
import com.lihan.vocabularynote.feature_allnotes.domain.repository.VocabularyNoteRepository
import com.lihan.vocabularynote.domain.use_cases.*
import com.lihan.vocabularynote.feature_tag.domain.use_cases.DeleteTag
import com.lihan.vocabularynote.feature_tag.domain.use_cases.GetAllTag
import com.lihan.vocabularynote.feature_tag.domain.use_cases.InsertTag
import com.lihan.vocabularynote.feature_tag.domain.use_cases.TagUseCases
import com.lihan.vocabularynote.domain.use_cases.vocabulary.*
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

}