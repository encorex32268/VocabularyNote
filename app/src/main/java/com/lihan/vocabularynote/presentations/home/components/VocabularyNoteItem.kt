package com.lihan.vocabularynote.presentations.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.domain.model.VocabularyNote

@Composable
fun VocabularyNoteItem(
    modifier: Modifier = Modifier,
    vocabularyNote: VocabularyNote,
    onItemClick : (VocabularyNote) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onItemClick(vocabularyNote)
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Green)
                .padding(8.dp)
                .align(Alignment.TopStart)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isShowSmallHiraganaText =
                vocabularyNote.hiraganaOrKatakana.isNotBlank() &&
                        vocabularyNote.word.isNotBlank()
            if (isShowSmallHiraganaText){
                Text(
                    text = vocabularyNote.hiraganaOrKatakana,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = vocabularyNote.word,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }else{
                Text(
                    text = vocabularyNote.hiraganaOrKatakana,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }


        }




    }


}