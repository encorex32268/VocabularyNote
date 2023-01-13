package com.lihan.vocabularynote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.feature_allnotes.presentations.add.InsertEditScreen
import com.lihan.vocabularynote.feature_allnotes.presentations.exam.ExamScreen
import com.lihan.vocabularynote.feature_allnotes.presentations.home.HomeScreen
import com.lihan.vocabularynote.info.InfoScreen
import com.lihan.vocabularynote.settings.SettingsScreen
import com.lihan.vocabularynote.storage.StorageScreen
import com.lihan.vocabularynote.feature_tag.presentations.TagScreen
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocabularyNoteTheme {
                val navController = rememberNavController()
                NavHost(navController =  navController, startDestination = Route.TAG){
                    composable(route =Route.HOME){
                        HomeScreen(
                            onNavigation = {
                                when(it){
                                    Route.ADD_EDIT ->{
                                        navController.navigate(Route.ADD_EDIT+ "/-1")
                                    }
                                    else->{
                                        navController.navigate(it)
                                    }
                                }
                            }
                        )
                    }
                    composable(
                        route = Route.ADD_EDIT + "/{note_id}",
                        arguments = listOf(
                            navArgument("note_id"){
                                type = NavType.IntType
                            }
                        )
                        ){
                        val noteId = it.arguments?.getInt("note_id")?:-1
                        InsertEditScreen(
                            noteId = noteId,
                            navController = navController
                        )
                    }
                    composable(
                        route = Route.EXAM
                    ){
                        ExamScreen()
                    }
                    composable(
                        route = Route.STORAGE
                    ){
                        StorageScreen(
                            onAddNewStorageClicked = {},
                            onCloseButtonClicked = {
                                navController.navigate(
                                    route = Route.HOME,
                                    navOptions = NavOptions.Builder().setPopUpTo(
                                        route = Route.HOME,
                                        inclusive = true,
                                        saveState = false
                                    ).build()
                                )
                            }
                        )
                    }
                    composable(
                        route = Route.TAG
                    ){
                        TagScreen(
                            onCloseButtonClicked = {
                                navController.navigate(
                                    route = Route.HOME,
                                    navOptions = NavOptions.Builder().setPopUpTo(
                                        route = Route.HOME,
                                        inclusive = true,
                                        saveState = false
                                    ).build()
                                )
                            }
                        )
                    }
                    composable(
                        route = Route.INFO
                    ){
                        InfoScreen(
                            onCloseButtonClicked = {
                                navController.navigate(
                                    route = Route.HOME,
                                    navOptions = NavOptions.Builder().setPopUpTo(
                                        route = Route.HOME,
                                        inclusive = true,
                                        saveState = false
                                    ).build()
                                )
                            }
                        )
                    }
                    composable(
                        route = Route.SETTINGS
                    ){
                        SettingsScreen(
                            onCloseButtonClicked = {
                                navController.navigate(
                                    route = Route.HOME,
                                    navOptions = NavOptions.Builder().setPopUpTo(
                                        route = Route.HOME,
                                        inclusive = true,
                                        saveState = false
                                    ).build()
                                )
                            }
                        )
                    }
                }

            }
        }
    }
}
