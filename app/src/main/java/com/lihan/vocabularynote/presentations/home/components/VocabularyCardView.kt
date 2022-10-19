package com.lihan.vocabularynote.presentations.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VocabularyCardViewFront(
    modifier: Modifier = Modifier,
    word : String = "大丈夫",
    color : Color = Color.Green,
    showDropMenu : () -> Unit  = {}
) {
    var typeColor by remember {
        mutableStateOf(color)
    }
    Box (
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .shadow(
                elevation = 10.dp
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            ,
        contentAlignment = Alignment.Center
    ){
        var expanded by remember{
            mutableStateOf(false)
        }
        val colors = listOf<Color>(
            Color.Red , Color.Black , Color.Blue, Color.Yellow
        )
        Box(modifier = Modifier
            .padding(16.dp)
            .size(40.dp)
            .background(
                color = typeColor,
                shape = CircleShape
            )
            .clickable {
                expanded = !expanded
            }
            .align(Alignment.TopStart)
        ){
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false}
            ) {
                colors.forEach { color ->
                    DropdownMenuItem(
                        onClick = {
                        typeColor = color
                        expanded = false
                    }) {
                        Box(modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = color,
                                shape = CircleShape
                            )
                        )
                    }

                }

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = word,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }

    }

}



@Composable
fun VocabularyCardViewBack(
    modifier: Modifier = Modifier,
    explain : String = "e12e2eqwtqweweqweewqewqeqweweqwewqeqweqewqewqewqewqeqwewqeeweqwewqewqeqweqwewqeqwewqeeweweeweweweeweweweweweqeqwewqeqweqeqweqwewqewwqe"
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .shadow(
                elevation = 10.dp
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        var text by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                maxLines = 4,
                value = explain,
                onValueChange ={
                  text = explain
                }, label = {
                    Text(text = "Input")
                },
                readOnly = true
            )


        }



    }


}