package com.lihan.vocabularynote.feature_allnotes.presentations.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lihan.vocabularynote.R
import com.lihan.vocabularynote.feature_allnotes.presentations.home.components.DropdownMenuSpinner
import com.lihan.vocabularynote.feature_allnotes.presentations.home.components.MultipleActionItem
import com.lihan.vocabularynote.feature_allnotes.presentations.home.components.MultipleFloatingActionButton
import com.lihan.vocabularynote.core.presentations.componets.SearchBar
import com.lihan.vocabularynote.core.presentations.componets.TitleText
import com.lihan.vocabularynote.feature_allnotes.presentations.home.components.navigationdrawer.DrawerBody
import com.lihan.vocabularynote.feature_allnotes.presentations.home.components.navigationdrawer.DrawerHeader
import com.lihan.vocabularynote.feature_allnotes.presentations.home.components.navigationdrawer.DrawerItem
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.ui.LocalSpacing
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigation : (String) -> Unit
) {
    val spacer = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var expanded by remember {
        mutableStateOf(false)
    }
    viewModel.onEvent(HomeEvent.GetNotes)
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        drawerContent = {
             DrawerHeader()
             DrawerBody(items = listOf(
                 DrawerItem(
                     imageVector = Icons.Default.Home,
                     name = Route.HOME
                 ),
                 DrawerItem(
                     imageVector = Icons.Default.Storage,
                     name = Route.STORAGE
                 ),
                 DrawerItem(
                     imageVector = Icons.Default.Tag,
                     name = Route.TAG
                 ),
                 DrawerItem(
                     imageVector = Icons.Default.Info,
                     name = Route.INFO
                 ),
                 DrawerItem(
                     imageVector = Icons.Default.Settings,
                     name = Route.SETTINGS
                 )
             ), onItemClick = {
                 if(it.name == Route.HOME){
                     scope.launch {
                         scaffoldState.drawerState.close()
                         expanded = false
                     }
                 }else{
                     onNavigation(it.name)
                 }
             })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        floatingActionButton = {
            MultipleFloatingActionButton(
                multipleActionItems = listOf(
                    MultipleActionItem(
                        name = Route.ADD_EDIT,
                        icon = Icons.Default.Add
                    ),
                    MultipleActionItem(
                        name = Route.EXAM,
                        icon = Icons.Default.List
                    )

                ),
                onFloatingButtonClick = {
                    when(it.name){
                        Route.EXAM->{
                            onNavigation(Route.EXAM)
                        }
                        Route.ADD_EDIT->{
                            focusManager.clearFocus()
                            onNavigation(Route.ADD_EDIT)
                        }
                    }
                }
            )
        }
    ) {
        Column (
            modifier = modifier.fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacer.spaceMedium, end = spacer.spaceMedium),
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
                        modifier = Modifier.size(36.dp),
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
                        viewModel.onEvent(HomeEvent.SearchByString(it))
                    },
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        viewModel.onEvent(HomeEvent.SearchByString(viewModel.state.searchText))
                    },
                    text = viewModel.state.searchText,
                    onFocusChanged = {
                        viewModel.onEvent(HomeEvent.ChangeHintVisible(it.isFocused))
                    },
                    shouldShowHint = viewModel.state.isHintVisible
                )

            }
            Spacer(modifier = Modifier.height(spacer.spaceExtraSmall))
            var spinnerText  by remember {
                mutableStateOf("AllNotes")
            }
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
                    dropMenuItems = listOf(
                        "AllNotes",
                        "Japanese",
                        "English",
                        "Chinese",
                        "Shop",
                    ),
                    isDrop = expanded,
                    onDismissRequest = {
                       expanded = false
                    },
                    onDropItemSelected = {
                        spinnerText = it
                        expanded = false

                    }
                )

                TitleText(
                    text = spinnerText
                )
                Spacer(modifier = Modifier.width(spacer.spaceExtraSmall))
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "ArrowDropDown",
                    tint = Color.Black
                )

            }

//            LazyColumn{
//                items(viewModel.state.notes){ note ->
//                    VocabularyNoteItem(
//                        vocabularyNote = note,
//                        onItemClick = {
//                            onNavigationToAddEdit(it)
//                        }
//                    )
//                }
//            }

        }
    }




}