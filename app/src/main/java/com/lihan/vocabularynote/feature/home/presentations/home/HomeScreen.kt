package com.lihan.vocabularynote.feature.home.presentations.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.core.domain.model.User
import com.lihan.vocabularynote.feature.home.presentations.home.components.DropdownMenuSpinner
import com.lihan.vocabularynote.core.presentations.componets.SearchBar
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.feature.home.presentations.home.components.navigationdrawer.DrawerBody
import com.lihan.vocabularynote.feature.home.presentations.home.components.navigationdrawer.DrawerHeader
import com.lihan.vocabularynote.feature.home.presentations.home.components.navigationdrawer.DrawerItem
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing
import com.lihan.vocabularynote.feature.home.domain.model.VocabularyNote
import com.lihan.vocabularynote.feature.home.presentations.home.components.VocabularyNoteItem
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigation : (String) -> Unit,
    state : HomeState ,
    onEvent : (HomeEvent) -> Unit,
    onNewNoteButtonClicked : (Int) -> Unit
) {
    val spacer = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var expanded by remember {
        mutableStateOf(false)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        drawerContent = {
             DrawerHeader(
                 userName = state.userName,
                 userIcon = state.userIcon,
                 onSaveName = {
                 onEvent(HomeEvent.SaveUserName(it))
                 },
                 onSelectedImage = {
                     onEvent(HomeEvent.SaveUserIcon(it))
                 }
             )
             DrawerBody(items = listOf(
                 DrawerItem(
                     imageVector = Icons.Default.Home,
                     name = stringResource(id = R.string.home),
                     routeName = Route.HOME
                 ),
                 DrawerItem(
                     imageVector = Icons.Default.FolderOpen,
                     name = stringResource(id = R.string.storage),
                     routeName = Route.STORAGE
                 ),
                 DrawerItem(
                     imageVector = Icons.Default.Tag,
                     name = stringResource(id = R.string.tag),
                     routeName = Route.TAG
                 ),
             ), onItemClick = {
                 if(it.routeName == Route.HOME){
                     scope.launch {
                         scaffoldState.drawerState.close()
                         expanded = false
                     }
                 }else{
                     onNavigation(it.routeName)
                 }
             })


        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        floatingActionButton = {
            if (state.showingStorage != null){
                FloatingActionButton(
                    onClick = {
                            onNewNoteButtonClicked(
                                state.showingStorage.storageId
                            )
                    })
                {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Button"
                    )
                }
            }
        }
//        floatingActionButton = {
//            MultipleFloatingActionButton(
//                multipleActionItems = listOf(
//                    MultipleActionItem(
//                        name = Route.ADD_EDIT,
//                        icon = Icons.Default.Add
//                    ),
//                    MultipleActionItem(
//                        name = Route.EXAM,
//                        icon = Icons.Default.List
//                    )
//
//                ),
//                onFloatingButtonClick = {
//                    when(it.name){
//                        Route.EXAM->{
//                            onNavigation(Route.EXAM)
//                        }
//                        Route.ADD_EDIT->{
//                            focusManager.clearFocus()
//                            onNavigation(Route.ADD_EDIT)
//                        }
//                    }
//                }
//            )
//        }
    ) {
        Column (
            modifier = modifier.fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = spacer.spaceMedium,
                        end = spacer.spaceMedium,
                        top = spacer.spaceMedium
                    ),
            ) {
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(top = spacer.spaceMedium),
                        painter = painterResource(id = R.drawable.icon_menu),
                        contentDescription = "Drawer Menu"
                    )
                }
                Spacer(modifier = Modifier.width(spacer.spaceExtraSmall))
                SearchBar(
                    modifier = Modifier
                        .weight(9f)
                        .padding(spacer.spaceSmall)
                    ,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacer.spaceMedium)
                    .clickable {
                        expanded = !expanded
                    }
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DropdownMenuSpinner(
                    dropMenuItems = state.storages,
                    isDrop = expanded,
                    onDismissRequest = {
                       expanded = false
                    },
                    onDropItemSelected = {
                        expanded = false
                        if (it.id == -1){
                            onEvent(HomeEvent.SpinnerStorageChanged(null))
                            onEvent(HomeEvent.GetAllVocabularyNotes)
                        }else{
                            onEvent(HomeEvent.SpinnerStorageChanged(it))
                            onEvent(HomeEvent.GetNotesByStorageId(it.storageId))
                        }
                    }
                )
                TitleText(
                    modifier = Modifier.weight(1f),
                    text = if (state.showingStorage == null) stringResource(id = R.string.home_all_storage) else state.showingStorage.name
                )
                Spacer(modifier = Modifier.width(spacer.spaceExtraSmall))
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "ArrowDropDown",
                    tint = Color.Black
                )

            }

            LazyColumn{
                items(state.notes){note ->
                    VocabularyNoteItem(
                        vocabularyNote = note,
                        onItemClick = {}
                    )

                }
            }

        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun HomeScreenPreView(){
    VocabularyNoteTheme {
        HomeScreen(
            onNavigation = { string -> },
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
                spinnerSelected = "WEWER",
                isHintVisible = false,
                searchText = "Box",
                userName = "Test",
                userIcon = User.icons[1]
            ),
            onEvent = {},
            onNewNoteButtonClicked = {_->}
        )
    }
}