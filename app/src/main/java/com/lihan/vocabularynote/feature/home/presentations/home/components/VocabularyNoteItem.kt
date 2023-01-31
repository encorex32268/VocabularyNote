package com.lihan.vocabularynote.feature.home.presentations.home.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote

@Composable
fun VocabularyNoteItem(
    modifier: Modifier = Modifier,
    vocabularyNote: VocabularyNote,
    onItemClick: (Int) -> Unit = {},
    typeCircleSize: Dp = 40.dp,
    noteCardHeight: Dp = 150.dp,
    hiraganaFontSize: TextUnit = 16.sp,
    wordFontSize: TextUnit = 30.sp,
    explainFontSize: TextUnit = 18.sp,
    isShowEdit: Boolean = false,
    onEditClick: (() -> Unit?)? =null
) {
    Log.d("TAG", "VocabularyNoteItem: ${vocabularyNote.type}")
    val spacer = LocalSpacing.current
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
                .padding(spacer.spaceMedium)
                .shadow(
                    shape = RoundedCornerShape(10.dp),
                    elevation = 5.dp
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
                Canvas(modifier = Modifier
                    .padding(spacer.spaceMedium)
                    .size(typeCircleSize)
                    .align(Alignment.TopStart)
                    .pointerInput(true) {
                        detectTapGestures {
                            onItemClick(vocabularyNote.id!!)
                        }
                    }
                ){
                    drawCircle(
                        color = if (vocabularyNote.type == -1) Color.Black else Color(vocabularyNote.type),
                        radius = typeCircleSize.toPx() / 2,
                        style = if (vocabularyNote.type == -1){
                            Stroke(
                                width = 1f
                            )
                        }else{
                            Fill
                        }
                    )

                }
                if (isShowEdit){
                   IconButton(
                       modifier = Modifier.align(Alignment.TopEnd),
                       onClick = {
                       if (onEditClick != null) {
                           onEditClick()
                       }
                   }) {
                       Icon(
                           imageVector = Icons.Default.Edit,
                           contentDescription = "VocabularyNoteEdit"
                       )
                   }
                }

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
                        Spacer(modifier = Modifier.height(spacer.spaceExtraTooSmall))
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
                        modifier = Modifier.padding(spacer.spaceMedium),
                        text = vocabularyNote.explain,
                        fontSize = explainFontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

    }


}