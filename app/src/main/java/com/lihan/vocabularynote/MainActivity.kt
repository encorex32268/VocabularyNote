package com.lihan.vocabularynote

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.gson.Gson
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.feature.home.presentations.add.InsertEditScreen
import com.lihan.vocabularynote.feature.home.presentations.exam.ExamScreen
import com.lihan.vocabularynote.feature.home.presentations.home.HomeScreen
import com.lihan.vocabularynote.feature.tag.domain.model.Tag
import com.lihan.vocabularynote.feature.info.presentations.InfoScreen
import com.lihan.vocabularynote.feature.settings.presentations.SettingsScreen
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.presentations.StorageScreen
import com.lihan.vocabularynote.feature.storage.presentations.StorageViewModel
import com.lihan.vocabularynote.feature.storage.presentations.edit.StorageEditScreen
import com.lihan.vocabularynote.feature.storage.presentations.edit.StorageEditViewModel
import com.lihan.vocabularynote.feature.storage.util.AssetStorageType
import com.lihan.vocabularynote.feature.tag.presentations.TagScreen
import com.lihan.vocabularynote.feature.tag.presentations.TagViewModel
import com.lihan.vocabularynote.feature.tag.presentations.add.TagAddScreen
import com.lihan.vocabularynote.feature.tag.presentations.add.TagAddViewModel
import com.lihan.vocabularynote.feature.tag.util.AssetTagType
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProvideWindowInsets(windowInsetsAnimationsEnabled = false) {
                VocabularyNoteTheme {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Route.HOME) {
                        composable(route = Route.HOME) {
                            HomeScreen(
                                onNavigation = {
                                    when (it) {
                                        Route.ADD_EDIT -> {
                                            navController.navigate(Route.ADD_EDIT + "/-1")
                                        }
                                        else -> {
                                            navController.navigate(it)
                                        }
                                    }
                                }
                            )
                        }
                        composable(
                            route = Route.ADD_EDIT + "/{note_id}/{storageId}",
                            arguments = listOf(
                                navArgument("note_id") {
                                    type = NavType.IntType
                                },
                                navArgument("storageId"){
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val noteId = it.arguments?.getInt("note_id") ?: -1
                            val storageId = it.arguments?.getInt("storageId")?:-1
                            InsertEditScreen(
                                noteId = noteId,
                                navController = navController,
                                storageId = storageId,
                                onTagAddClicked = {
                                    navController.navigate(
                                        route = Route.TAG,
                                        navOptions = NavOptions.Builder()
                                            .setPopUpTo(
                                                route = Route.TAG,
                                                inclusive = true
                                            ).build()
                                    )
                                }
                            )
                        }
                        composable(
                            route = Route.EXAM
                        ) {
                            ExamScreen()
                        }
                        composable(
                            route = Route.STORAGE
                        ) {
                            val viewModel = hiltViewModel<StorageViewModel>()
                            val state = viewModel.storageState
                            val uiEvent = viewModel.uiEvent
                            StorageScreen(
                                onCloseButtonClicked = {
                                    navController.navigate(
                                        route = Route.HOME,
                                        navOptions = NavOptions.Builder().setPopUpTo(
                                            route = Route.HOME,
                                            inclusive = true,
                                            saveState = false
                                        ).build()
                                    )
                                },
                                onEditStorageClicked = {
                                    navController.navigate(
                                        route = Route.STORAGE_EDIT + "/${Uri.encode(Gson().toJson(it))}"
                                    )
                                },
                                storageState = state,
                                uiEvent = uiEvent,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable(
                            route = Route.STORAGE_EDIT + "/{storage}",
                            arguments = listOf(
                                navArgument("storage"){
                                    type = AssetStorageType()
                                }
                            )
                        ){
                            val storage = it.arguments?.getParcelable("storage")?:Storage()
                            val viewModel = hiltViewModel<StorageEditViewModel>()
                            val state = viewModel.state
                            val uiEvent = viewModel.uiEvent
                            StorageEditScreen(
                                storage = storage,
                                state = state,
                                uiEvent = uiEvent,
                                onEvent = viewModel::onEvent,
                                onCloseButtonClicked = {
                                    navController.navigateUp()
                                },
                                onNewVocabularyNoteClicked = {
                                    navController.navigate(Route.ADD_EDIT + "/-1/$it")
                                },
                                onEditVocabularyNoteClicked ={ noteid , storageId ->
                                    navController.navigate(Route.ADD_EDIT + "/$noteid/$storageId")
                                }
                            )
                        }

                        composable(
                            route = Route.TAG
                        ) {
                            val viewModel = hiltViewModel<TagViewModel>()
                            val state = viewModel.tagState
                            TagScreen(
                                state,
                                onCloseButtonClicked = {
                                    navController.navigate(
                                        route = Route.HOME,
                                        navOptions = NavOptions.Builder().setPopUpTo(
                                            route = Route.HOME,
                                            inclusive = true,
                                            saveState = false
                                        ).build()
                                    )
                                },
                                onNavigationNewTag = {
                                    navController.navigate(
                                        route = Route.TAG_ADD_EDIT + "/${Uri.encode(Gson().toJson(
                                            Tag()
                                        ))}"
                                    )
                                },
                                onNavigationEditTag ={
                                    navController.navigate(
                                        route = Route.TAG_ADD_EDIT + "/${Uri.encode(Gson().toJson(it))}"
                                    )
                                }
                            )
                        }


                        composable(
                            route = Route.TAG_ADD_EDIT + "/{tag}",
                            arguments = listOf(
                                navArgument("tag") {
                                    type = AssetTagType()
                                }
                            )
                        ) {
                            val tag = it.arguments?.getParcelable<Tag>("tag")?: Tag()
                            val viewModel = hiltViewModel<TagAddViewModel>()
                            val state = viewModel.tagAddState
                            TagAddScreen(
                                onEvent = viewModel::onEvent,
                                onCloseButtonClicked = {
                                    navController.navigateUp()
                                },
                                tag = tag,
                                tagAddState = state
                            )
                        }

//                        composable(
//                            route = Route.INFO
//                        ) {
//                            InfoScreen(
//                                onCloseButtonClicked = {
//                                    navController.navigate(
//                                        route = Route.HOME,
//                                        navOptions = NavOptions.Builder().setPopUpTo(
//                                            route = Route.HOME,
//                                            inclusive = true,
//                                            saveState = false
//                                        ).build()
//                                    )
//                                }
//                            )
//                        }
//                        composable(
//                            route = Route.SETTINGS
//                        ) {
//                            SettingsScreen(
//                                onCloseButtonClicked = {
//                                    navController.navigate(
//                                        route = Route.HOME,
//                                        navOptions = NavOptions.Builder().setPopUpTo(
//                                            route = Route.HOME,
//                                            inclusive = true,
//                                            saveState = false
//                                        ).build()
//                                    )
//                                }
//                            )
//                        }
                    }

                }

            }
        }
    }
}
