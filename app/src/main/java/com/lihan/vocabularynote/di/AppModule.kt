package com.lihan.vocabularynote.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.lihan.vocabularynote.data.VocabularyNoteDatabase
import com.lihan.vocabularynote.data.repository.VocabularyNoteRepositoryImpl
import com.lihan.vocabularynote.domain.repository.VocabularyNoteRepository
import com.lihan.vocabularynote.domain.use_cases.*
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
    ) : VocabularyNoteDatabase{
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
    fun providesVocabularyNoteUseCases(vocabularyNoteRepository: VocabularyNoteRepository) : VocabularyNoteUseCases{
        return VocabularyNoteUseCases(
            GetVocabularyNotes(vocabularyNoteRepository = vocabularyNoteRepository),
            GetVocabularyByNoteId(vocabularyNoteRepository = vocabularyNoteRepository),
            DeleteVocabularyNote(vocabularyNoteRepository = vocabularyNoteRepository),
            InsertEditVocabularyNote(vocabularyNoteRepository = vocabularyNoteRepository),
        )
    }



}