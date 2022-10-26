package com.lihan.vocabularynote.presentations.add

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.lihan.vocabularynote.core.componets.appbar.MenuID
import com.lihan.vocabularynote.core.componets.appbar.MenuItem
import com.lihan.vocabularynote.core.componets.appbar.VocabularyNoteAppBar
import com.lihan.vocabularynote.domain.model.VocabularyNote


@ExperimentalComposeUiApi
@Composable
fun InsertEditScreen(
    modifier: Modifier = Modifier,
    noteId: Int = -1,
    navController: NavController,
    viewModel : InsertEditViewModel = hiltViewModel()
) {

    var isEditPage by remember {
        mutableStateOf(false)
    }
    val focusRequester  = FocusRequester()
    val (first, second,third) = FocusRequester.createRefs()
    val focusManager = LocalFocusManager.current


    LaunchedEffect(key1 = noteId){
        if (noteId != -1){
            isEditPage = true
            viewModel.onEvent(InsertEditEvent.IsEditPage(noteId))
        }
        focusRequester.requestFocus()
    }

    val state = viewModel.state

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(InsertEditEvent.Save)
                    navController.popBackStack()
                })
            {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Check"
                )
            }
        }
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .pointerInput(LocalContext.current) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }

        ){

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart),
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "BackButton")
            }
            if (isEditPage){
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),onClick = {
                        viewModel.onEvent(InsertEditEvent.Remove(noteId))
                    }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Button")
                }
            }else{
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),onClick = {
                        navController.popBackStack()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "CloseButton")
                }
            }




            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                var expanded by remember {
                    mutableStateOf(false)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                        .shadow(
                            elevation = 10.dp
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {

                    Box(modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                        .background(
                            color = Color(state.type),
                            shape = CircleShape
                        )
                        .clickable {
                            expanded = !expanded
                        }
                        .align(Alignment.TopStart)
                    ) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            VocabularyNote.typeColors.forEach { color ->
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.onEvent(InsertEditEvent.TypeColorChanged(color))
                                        expanded = false
                                    }) {
                                    Box(
                                        modifier = Modifier
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
                            .padding(start = 60.dp, top = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .focusOrder(first) {
                                    down = second
                                }
                            ,
                            value = state.hiraganaOrKatakana,
                            onValueChange = {
                                viewModel.onEvent(InsertEditEvent.HiraganaChanged(it))
                            },
                            label = { Text(text = "Phonetics") },
                            singleLine = true,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            modifier = Modifier.focusOrder(second){
                                down = third
                            },
                            value = state.word,
                            onValueChange = {
                                viewModel.onEvent(InsertEditEvent.WordChanged(it))
                            },
                            label = { Text(text = "Word") },
                            singleLine = true,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            )
                        )

                    }


                }

                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                        .shadow(
                            elevation = 10.dp
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .focusOrder(third)
                        ,
                        value = state.explain,
                        onValueChange = {
                            viewModel.onEvent(InsertEditEvent.ExplainChanged(it))
                        },
                        label = { Text(text = "Explain") }
                    )


                }

            }

        }



    }


}