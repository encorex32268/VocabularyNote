package com.lihan.vocabularynote.core.presentations.componets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.ColorUtils
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import java.lang.String
import kotlin.random.Random

@Composable
fun DialogColorPicker(
   modifier: Modifier = Modifier,
   onColorSend : (ColorEnvelope) -> Unit,
   isShowDialog : Boolean = false,
   defaultColor : Int = 0,
) {
    val color = Color(defaultColor)
    val hexColor = String.format("#%06X", 0xFFFFFF and color.toArgb())
    var selectColor by remember {
        mutableStateOf(
            ColorEnvelope(
                color = Color(defaultColor),
                hexCode = hexColor,
                fromUser = true
            )
        )
    }
    var dialogShow by remember {
        mutableStateOf(isShowDialog)
    }
    if (dialogShow){
        Dialog(
            onDismissRequest = {
                onColorSend(selectColor)
                dialogShow = false
            },
            properties = DialogProperties(
                dismissOnBackPress = true
            )
        ) {
            val colorPickerController = rememberColorPickerController()
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HsvColorPicker(
                    modifier = Modifier.weight(1.5f),
                    controller =colorPickerController,
                    onColorChanged = {
                        selectColor = it
                    }
                )
                Row(
                    modifier = Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(selectColor.color)
                            .clickable {
                                onColorSend(selectColor)
                                dialogShow = false
                            },
                        contentAlignment = Alignment.Center
                    ){
                        val isColorDark = ColorUtils.calculateLuminance(selectColor.color.toArgb()) < 0.25
                        Text(
                            text = "Ok",
                            color = if (isColorDark){
                                Color.White
                            }else{
                                Color.Black
                            }
                        )
                    }
                }

            }



        }

    }
//    fun isColorDark(color: Int): Boolean {
//        val darkness: Double =
//            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
//        return darkness >= 0.5
//    }

}


