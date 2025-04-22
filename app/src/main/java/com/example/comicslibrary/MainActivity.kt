package com.example.comicslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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


sealed class Destination(val route:String){
    object Library: Destination("Library")
    object Collection: Destination("collection")
    object CharacterDetail: Destination("character/characterId"){
        fun createRoute(characterId: Int?) = "character/$characterId"
    }

}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComicsLibraryTheme {
             Surface (
                 modifier = Modifier.fillMaxSize(),
                 color = MaterialTheme.colorScheme.background
             ){
                CharactersScaffold(navController = rememberNavController())
             }
            }
        }
    }
}

@Composable
fun CharactersScaffold(navController: NavHostController){
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
                LibraryScreen()
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



