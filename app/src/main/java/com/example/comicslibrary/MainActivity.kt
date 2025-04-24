package com.example.comicslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.comicslibrary.ui.theme.ComicsLibraryTheme
import com.example.comicslibrary.view.CharactersBottomNav
import com.example.comicslibrary.view.CollectionScreen
import com.example.comicslibrary.view.LibraryScreen
import com.example.comicslibrary.viewmodel.LibraryApiViewModel
import dagger.hilt.android.AndroidEntryPoint


sealed class Destination(val route:String){
    object Library: Destination("Library")
    object Collection: Destination("collection")
    object CharacterDetail: Destination("character/characterId"){
        fun createRoute(characterId: Int?) = "character/$characterId"
    }

}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val lvm by viewModels<LibraryApiViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComicsLibraryTheme {
             Surface (
                 modifier = Modifier.fillMaxSize(),
                 color = MaterialTheme.colorScheme.background
             ){
                CharactersScaffold(navController = rememberNavController(), lvm)
             }
            }
        }
    }
}

@Composable
fun CharactersScaffold(navController: NavHostController, lvm: LibraryApiViewModel){
    Scaffold(
        bottomBar = {
CharactersBottomNav(navController = navController)
        }
    ) {

      innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Library.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destination.Library.route){
                LibraryScreen(navController, lvm, paddingValues=innerPadding)
            }

            composable(Destination.Collection.route){
                CollectionScreen()
            }

            composable(Destination.CharacterDetail.route){
                navBackStackEntry ->
            }
        }
    }
}



