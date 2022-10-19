package com.lihan.vocabularynote.presentations.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.domain.model.VocabularyNote
import org.w3c.dom.Text

@Composable
fun VocabularyNoteItem(
    modifier: Modifier = Modifier,
    vocabularyNote: VocabularyNote,
    onItemClick : (Int) -> Unit = {},
    typeCircleSize : Dp = 40.dp,
    noteCardHeight : Dp = 150.dp,
    hiraganaFontSize : TextUnit = 12.sp,
    wordFontSize : TextUnit = 20.sp
) {
    var rotated by remember {
        mutableStateOf(false)
    }
    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    Box(
            modifier = modifier
                .fillMaxWidth()
                .height(noteCardHeight)
                .padding(16.dp)
                .shadow(
                    elevation = 10.dp
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .clickable {
                    rotated = !rotated
                },
            contentAlignment = Alignment.Center

        ) {
            if(rotation < 90) {
                //Front
                Box(modifier = Modifier
                    .padding(16.dp)
                    .size(typeCircleSize)
                    .background(
                        color = Color(vocabularyNote.type),
                        shape = CircleShape
                    )
                    .align(Alignment.TopStart)
                    .clickable {
                        onItemClick(vocabularyNote.id!!)
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val isShowSmallHiraganaText =
                        vocabularyNote.hiraganaOrKatakana.isNotBlank() &&
                                vocabularyNote.word.isNotBlank()
                    if (isShowSmallHiraganaText) {
                        Text(
                            text = vocabularyNote.hiraganaOrKatakana,
                            fontSize = hiraganaFontSize,
                            fontWeight = FontWeight.Light
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = vocabularyNote.word,
                            fontSize = wordFontSize,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = vocabularyNote.hiraganaOrKatakana,
                            fontSize = hiraganaFontSize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }else{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                             rotationY = 180f
                        }
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = vocabularyNote.explain,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

    }


}