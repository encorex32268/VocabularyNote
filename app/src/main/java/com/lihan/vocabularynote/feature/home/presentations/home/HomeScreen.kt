package com.lihan.vocabularynote.feature.home.presentations.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.presentations.componets.SearchBar
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.presentations.add.components.TagDialog
import com.lihan.vocabularynote.feature.home.presentations.home.components.VocabularyNoteItem
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.presentations.StorageEvent
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state : HomeState ,
    onEvent : (HomeEvent) -> Unit
) {
    val spacer = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val bottomScaffold = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = bottomScaffold,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            VocabularyInsertContent(
                state = state,
                onDismiss = {
                    scope.launch {
                        delay(300L)
                        bottomScaffold.bottomSheetState.collapse()
                    }
                },
                onEvent = onEvent
            )
        }
    ) {
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                TitleText(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = com.lihan.vocabularynote.R.string.home)
                )
                IconButton(
                    modifier = Modifier.padding(end = spacer.spaceSmall),
                    onClick = {
                    scope.launch {
                        if (bottomScaffold.bottomSheetState.isCollapsed){
                            bottomScaffold.bottomSheetState.expand()
                        }else{
                            bottomScaffold.bottomSheetState.collapse()
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = spacer.spaceMedium,
                        end = spacer.spaceMedium,
                        top = spacer.spaceMedium
                    ),
            ) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth() ,
                    onValueChange = {
                        onEvent(HomeEvent.SearchByString(it))
                    },
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        onEvent(HomeEvent.SearchByString(state.searchText))
                    },
                    text = state.searchText,
                    onFocusChanged = {
                        onEvent(HomeEvent.ChangeHintVisible(it.isFocused))
                    },
                    shouldShowHint = state.isHintVisible,
                    hintText = stringResource(id = R.string.home_search_vocabulary)
                )

            }
            Spacer(modifier = Modifier.height(spacer.spaceExtraSmall))
            LazyColumn{
                items(
                    items = state.notes,
                    key = {
                        it.id?:0
                    }
                ){note ->
                    VocabularyNoteItem(
                        vocabularyNote = note,
                        onItemClick = {}
                    )

                }
            }

        }

    }
    BackHandler(
        onBack = {
            if (bottomScaffold.bottomSheetState.isExpanded){
                scope.launch {
                    bottomScaffold.bottomSheetState.collapse()
                }
                return@BackHandler
            }
        }
    )
}

@Composable
private fun StoragesContent(
    modifier: Modifier = Modifier,
    items : List<Storage> = emptyList(),
    onClick : (Storage) -> Unit = {},
    selectedItem : Storage?
) {
    val spacer = LocalSpacing.current
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = spacer.spaceSmall
            )
    ){
        items(
            items,
            key = {
                it.hashCode()
            }
        ){ storage ->
            Card(
                modifier = Modifier
                    .padding(spacer.spaceSmall)
                    .clickable {
                        onClick(storage)
                    }
                ,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = if (selectedItem?.storageId == storage.storageId) Color.Black else Color.White,
                border = BorderStroke(
                    width = 1.dp, color = Color.Black
                )
            ) {
                Text(
                    modifier = Modifier.padding(spacer.spaceExtraSmall),
                    text = storage.name,
                    color = if (selectedItem?.storageId == storage.storageId) Color.White else Color.Black
                )
            }
        }
    }
}

@Composable
private fun VocabularyInsertContent(
    modifier: Modifier = Modifier,
    onDismiss : () -> Unit = {},
    state : HomeState,
    onEvent: (HomeEvent) -> Unit = {}
){
    val spacer = LocalSpacing.current
    var phonetics by remember {
        mutableStateOf("")
    }
    var word by remember {
        mutableStateOf("")
    }
    var explain by remember {
        mutableStateOf("")
    }
    var type by remember {
        mutableStateOf(0)
    }
    var selectedStorage by remember {
        mutableStateOf<Storage?>(null)
    }
    var isShowTagDialog by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            StoragesContent(
                items = state.storages,
                onClick = {
                    selectedStorage = it
                },
                selectedItem = selectedStorage
            )
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
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(spacer.spaceMedium)
                            .size(50.dp)
                            .pointerInput(true) {
                                detectTapGestures {
                                    isShowTagDialog = true
                                }
                            }
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = if (type == 0) Color.Black else Color(type)
                            )
                    )
                    if (isShowTagDialog){
                        TagDialog(
                            tags = state.tags,
                            onTagItemClicked = {
                                type = it.color
                                isShowTagDialog = false
                            },
                            onDismiss = {
                                isShowTagDialog = false
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
                            modifier = Modifier,
                            value =  phonetics,
                            onValueChange = {
                                phonetics = it
                            },
                            label = { Text(text = "Phonetics") },
                            singleLine = true,
                            keyboardActions = KeyboardActions(
                                onDone = {

                                }
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value =  word,
                            onValueChange = {
                                word = it
                            },
                            label = { Text(text = "Word") },
                            singleLine = true,
                            keyboardActions = KeyboardActions(
                                onDone = {

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
                    ,
                    value = explain,
                    onValueChange = {
                        explain = it
                    },
                    label = { Text(text = "Explain") }
                )


            }

        }
        FloatingActionButton(
            modifier = Modifier
                .padding(spacer.spaceSmall)
                .align(Alignment.BottomEnd),
            onClick = {
                selectedStorage?.let { storage ->
                    val vocabulary = VocabularyNote(
                        word = word,
                        hiraganaOrKatakana = phonetics,
                        roma = "",
                        createDate = Instant.now().toEpochMilli(),
                        explain = explain,
                        type = type,
                        storageId = storage.storageId
                    )
                    //Insert
                    onEvent(
                        HomeEvent.InsertVocabulary(vocabulary)
                    )
                }
                onDismiss()
        }) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )
        }

    }
}


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Preview
@Composable
fun HomeScreenPreView(){
    VocabularyNoteTheme {
        HomeScreen(
            state = HomeState(
                notes = listOf(
                    VocabularyNote(
                        type = 1,
                        word = "word",
                        hiraganaOrKatakana = "hira",
                        roma = "roma",
                        createDate = 23,
                        explain = "explain",
                        storageId = 2
                    ),
                    VocabularyNote(
                        type = 1,
                        word = "word2",
                        hiraganaOrKatakana = "hira",
                        roma = "roma",
                        createDate = 23,
                        explain = "explain",
                        storageId = 2
                    ),
                    VocabularyNote(
                        type = 1,
                        word = "word3",
                        hiraganaOrKatakana = "hira",
                        roma = "roma",
                        createDate = 23,
                        explain = "explain",
                        storageId = 2
                    )
                ),
                storages = listOf(),
                isHintVisible = false,
                searchText = "Box"
            ),
            onEvent = {}
        )
    }
}