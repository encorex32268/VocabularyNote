package com.lihan.vocabularynote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.core.presentations.componets.bottomnavigation.BottomItem
import com.lihan.vocabularynote.core.presentations.componets.bottomnavigation.BottomNavigation
import com.lihan.vocabularynote.feature.home.presentations.add.InsertEditScreen
import com.lihan.vocabularynote.feature.home.presentations.add.InsertEditViewModel
import com.lihan.vocabularynote.feature.home.presentations.exam.ExamScreen
import com.lihan.vocabularynote.feature.home.presentations.home.HomeScreen
import com.lihan.vocabularynote.feature.home.presentations.home.HomeViewModel
import com.lihan.vocabularynote.feature.storage.presentations.StorageScreen
import com.lihan.vocabularynote.feature.storage.presentations.StorageViewModel
import com.lihan.vocabularynote.feature.storage.util.AssetStorageType
import com.lihan.vocabularynote.feature.tag.presentations.TagScreen
import com.lihan.vocabularynote.feature.tag.presentations.TagViewModel
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                VocabularyNoteTheme {
                    val scaffoldState = rememberScaffoldState()
                    val navController = rememberNavController()
                    Scaffold(
                        scaffoldState = scaffoldState,
                        bottomBar = {
                            BottomNavigation(
                                modifier = Modifier.fillMaxWidth(),
                                items = listOf(
                                    BottomItem(
                                        icon = Icons.Default.Home,
                                        route = Route.HOME
                                    ),
                                    BottomItem(
                                        icon = Icons.Default.Folder,
                                        route = Route.STORAGE
                                    ),
                                    BottomItem(
                                        icon = Icons.Default.Tag,
                                        route = Route.TAG
                                    )
                                ),
                                onItemClicked = {
                                    navController.navigate(it.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                },
                                navController = navController,
                            )
                        }
                    ) {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            startDestination = Route.HOME
                        ) {
                            composable(route = Route.HOME) {
                                val viewModel = hiltViewModel<HomeViewModel>()
                                val state = viewModel.state
                                HomeScreen(
                                    state = state,
                                    onEvent = viewModel::onEvent
                                )
                            }
                            composable(
                                route = Route.ADD_EDIT + "/{note_id}/{storageId}",
                                arguments = listOf(
                                    navArgument("note_id") {
                                        type = NavType.IntType
                                    },
                                    navArgument("storageId") {
                                        type = NavType.IntType
                                    }
                                )
                            ) {
                                val noteId = it.arguments?.getInt("note_id") ?: -1
                                val storageId = it.arguments?.getInt("storageId") ?: -1
                                val viewModel = hiltViewModel<InsertEditViewModel>()
                                val state = viewModel.state
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
                                    },
                                    state = state,
                                    onEvent = viewModel::onEvent
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
                                    state = state,
                                    onEvent = viewModel::onEvent
                                )
                            }
                            composable(
                                route = Route.STORAGE_EDIT + "/{storage}",
                                arguments = listOf(
                                    navArgument("storage") {
                                        type = AssetStorageType()
                                    }
                                )
                            ) {
//                                val storage = it.arguments?.getParcelable("storage") ?: Storage()
//                                val viewModel = hiltViewModel<StorageEditViewModel>()
//                                val state = viewModel.state
//                                val uiEvent = viewModel.uiEvent
//                                StorageEditScreen(
//                                    storage = storage,
//                                    state = state,
//                                    uiEvent = uiEvent,
//                                    onEvent = viewModel::onEvent,
//                                    onCloseButtonClicked = {
//                                        navController.navigateUp()
//                                    },
//                                    onNewVocabularyNoteClicked = {
//                                        navController.navigate(Route.ADD_EDIT + "/-1/$it")
//                                    },
//                                    onEditVocabularyNoteClicked = { noteid, storageId ->
//                                        navController.navigate(Route.ADD_EDIT + "/$noteid/$storageId")
//                                    }
//                                )
                            }

                            composable(
                                route = Route.TAG
                            ) {
                                val viewModel = hiltViewModel<TagViewModel>()
                                val state = viewModel.tagState
                                TagScreen(
                                    tagState = state,
                                    onEvent = viewModel::onEvent
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
