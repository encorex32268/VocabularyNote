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

    Column (
            modifier = modifier.fillMaxSize()
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            TitleText(
                modifier = Modifier.weight(1f),
                text = stringResource(id = com.lihan.vocabularynote.R.string.home)
            )
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
                isHintVisible = false,
                searchText = "Box"
            ),
            onEvent = {},
            onNewNoteButtonClicked = {_->}
        )
    }
}