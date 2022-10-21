package com.lihan.vocabularynote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lihan.vocabularynote.core.navigation.Route
import com.lihan.vocabularynote.presentations.add.InsertEditScreen
import com.lihan.vocabularynote.presentations.home.HomeScreen
import com.lihan.vocabularynote.ui.theme.VocabularyNoteTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocabularyNoteTheme {
                val navController = rememberNavController()
                NavHost(navController =  navController, startDestination = Route.HOME){
                    composable(route =Route.HOME){
                        HomeScreen(
                            onNavigation = {
                                navController.navigate(
                                    route = Route.ADD_EDIT + "/$it"
                                )
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
                        val noteId = it.arguments?.getInt("note_id")?:0
                        InsertEditScreen(
                            noteId = noteId,
                            navController = navController
                        )
                    }
                }

            }
        }
    }
}
