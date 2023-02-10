package com.lihan.vocabularynote.feature.home.domain.use_cases

import android.util.Log
import com.lihan.vocabularynote.feature.home.data.repository.FakeVocabularyNoteRepository
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetVocabularyNotesTest {

    private lateinit var getVocabularyNotes: GetVocabularyNotes
    private lateinit var fakeVocabularyNoteRepository: FakeVocabularyNoteRepository

    @Before
    fun setUp(){
        fakeVocabularyNoteRepository = FakeVocabularyNoteRepository()
        getVocabularyNotes = GetVocabularyNotes(fakeVocabularyNoteRepository)
        val vocabularyNoteToInsert = mutableListOf<VocabularyNote>()
        ('a'..'z').forEachIndexed { index, c ->
            vocabularyNoteToInsert.add(
                VocabularyNote(
                    id = index,
                    type = 100,
                    word = "Word$c" ,
                    hiraganaOrKatakana = "HiraganaOrKatakana$c",
                    roma = c.toString(),
                    createDate = System.currentTimeMillis(),
                    explain = "Explain$c",
                    storageId = index % 2
                )
            )
        }
        vocabularyNoteToInsert.shuffle()
        runBlocking {
            vocabularyNoteToInsert.forEach { fakeVocabularyNoteRepository.insert(it) }
        }
    }

    @Test
    fun `Delete Note By Id`() = runBlocking{
        var oldSize = 0
        fakeVocabularyNoteRepository.getAllVocabulary().collectLatest {
            oldSize  = it.size
        }
        runBlocking {
            (0..9).forEach {
                fakeVocabularyNoteRepository.deleteVocabularyNote(it)
            }

        }
        var newSize = 0
        fakeVocabularyNoteRepository.getAllVocabulary().collectLatest {
            newSize =  it.size
        }
        assertTrue(oldSize != newSize)


    }

}