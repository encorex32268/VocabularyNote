package com.lihan.vocabularynote.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.lihan.vocabularynote.data.VocabularyNoteDatabase
import com.lihan.vocabularynote.data.preferences.DefaultPreferences
import com.lihan.vocabularynote.data.repository.VocabularyNoteRepositoryImpl
import com.lihan.vocabularynote.domain.repository.Preferences
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

    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context : Context) : SharedPreferences{
        return context.getSharedPreferences("user_data",Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesPreferences(sharedPreferences: SharedPreferences) : Preferences{
        return DefaultPreferences(sharedPreferences)
    }



}