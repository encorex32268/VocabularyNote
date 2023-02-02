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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.home.presentations.add.components.TagDialog


@ExperimentalComposeUiApi
@Composable
fun InsertEditScreen(
    modifier: Modifier = Modifier,
    noteId: Int = -1,
    navController: NavController,
    viewModel : InsertEditViewModel = hiltViewModel(),
    storageId : Int = -1,
    onTagAddClicked : () -> Unit,
    typeCircleSize: Dp = 40.dp,

    ) {
    val spacer = LocalSpacing.current

    var isEditPage by remember {
        mutableStateOf(false)
    }
    val focusRequester  = FocusRequester()
    val (first, second,third) = FocusRequester.createRefs()
    val focusManager = LocalFocusManager.current
    val dialogWidth = LocalConfiguration.current.screenWidthDp.dp / 2
    val state = viewModel.state
    LaunchedEffect(key1 = noteId){
        if (noteId != -1){
            isEditPage = true
            viewModel.onEvent(InsertEditEvent.IsEditPage(noteId))
        }else{
            viewModel.onEvent(InsertEditEvent.IsAddPage(storageId))
        }
        focusRequester.requestFocus()
    }


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
                        viewModel.onEvent(InsertEditEvent.Remove(noteId))
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

                    Canvas(modifier = Modifier
                        .padding(spacer.spaceMedium)
                        .size(typeCircleSize)
                        .align(Alignment.TopStart)
                        .pointerInput(true) {
                            detectTapGestures {
                                expanded = !expanded
                            }
                        }
                    ){
                        var type = 0
                        state.vocabularyNote?.let {
                            type = it.type
                        }
                        drawCircle(
                            color = if (type == 0) Color.Black else Color(type),
                            radius = typeCircleSize.toPx() / 2,
                            style = if (type == 0){
                                Stroke(
                                    width = 1f
                                )
                            }else{
                                Fill
                            }
                        )
                    }
                    if (expanded){
                        TagDialog(
                            modifier = Modifier
                                .background(Color.White)
                                .width(dialogWidth)
                            ,
                            isShow = expanded,
                            tags = viewModel.state.tags,
                            onAddTagClicked = {
                                expanded = false
                                onTagAddClicked()
                            },
                            onTagItemClicked = {
                                expanded = false
                                viewModel.onEvent(InsertEditEvent.TypeColorChanged(it.color))
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
                            value = viewModel.state.vocabularyNote?.hiraganaOrKatakana?:"",
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
                            value = viewModel.state.vocabularyNote?.word?:"",
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
                        value = viewModel.state.vocabularyNote?.explain?:"",
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