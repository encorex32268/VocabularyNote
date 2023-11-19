package com.lihan.vocabularynote.feature.home.presentations.add

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.presentations.add.components.TagDialog
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme


@ExperimentalComposeUiApi
@Composable
fun InsertEditScreen(
    modifier: Modifier = Modifier,
    noteId: Int = -1,
    navController: NavController,
    storageId : Int = -1,
    onTagAddClicked : () -> Unit,
    typeCircleSize: Dp = 40.dp,
    state : InsertEditState,
    onEvent : (InsertEditEvent) -> Unit
) {
    val spacer = LocalSpacing.current

    var isEditPage by remember {
        mutableStateOf(false)
    }
    val focusRequester  = FocusRequester()
    val (first, second,third) = FocusRequester.createRefs()
    val focusManager = LocalFocusManager.current
    val dialogWidth = LocalConfiguration.current.screenWidthDp.dp / 2
    LaunchedEffect(key1 = noteId){
        if (noteId != -1){
            isEditPage = true
            onEvent(InsertEditEvent.IsEditPage(noteId))
        }else{
            onEvent(InsertEditEvent.IsAddPage(storageId))
        }
        focusRequester.requestFocus()
    }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(InsertEditEvent.Save)
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
            .padding(spacer.spaceSmall)
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
                        onEvent(InsertEditEvent.Remove(noteId))
                        navController.popBackStack()
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
                        .padding(spacer.spaceMedium)
                        .shadow(
                            elevation = 10.dp
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Row {
                        var type = 0
                        state.vocabularyNote?.let {
                            type = it.type
                        }
                        Box(
                            modifier = Modifier
                                .padding(spacer.spaceMedium)
                                .size(25.dp)
                                .pointerInput(true) {
                                    detectTapGestures {
                                        expanded = !expanded
                                    }
                                }
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .background(
                                    color = if (type == 0) Color.Black else Color(type)
                                )
                        )


                        if (expanded){
                            TagDialog(
                                modifier = Modifier
                                    .background(Color.White)
                                    .width(dialogWidth)
                                ,
                                tags = state.tags,
                                onTagItemClicked = {
                                    expanded = false
                                    onEvent(InsertEditEvent.TypeColorChanged(it.color))
                                }
                            )

                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = spacer.spaceExtraLarge,
                                    top = spacer.spaceMedium,
                                    end = spacer.spaceMedium,
                                ),
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
                                value = state.vocabularyNote?.hiraganaOrKatakana?:"",
                                onValueChange = {
                                    onEvent(InsertEditEvent.HiraganaChanged(it))
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
                                value = state.vocabularyNote?.word?:"",
                                onValueChange = {
                                    onEvent(InsertEditEvent.WordChanged(it))
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


                }

                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(spacer.spaceMedium)
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
                            .padding(spacer.spaceMedium)
                            .focusOrder(third)
                        ,
                        value = state.vocabularyNote?.explain?:"",
                        onValueChange = {
                            onEvent(InsertEditEvent.ExplainChanged(it))
                        },
                        label = { Text(text = "Explain") }
                    )


                }

            }

        }



    }


}

@ExperimentalComposeUiApi
@Preview
@Composable
fun InsertEditScreenPreView(){

    VocabularyNoteTheme {
        val context = LocalContext.current
        InsertEditScreen(
            navController = NavController(context),
            onTagAddClicked = {},
            state = InsertEditState(
                storageId = 1,
                vocabularyNote = VocabularyNote(
                    type = 1,
                    word = "Edit Word",
                    hiraganaOrKatakana = "Hira",
                    createDate = 22123,
                    roma = "Roma",
                    explain = "Explain",
                    storageId = 2
                )
            ),
            onEvent = { _->}
        )
    }


}