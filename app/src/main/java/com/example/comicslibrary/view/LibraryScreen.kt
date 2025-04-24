package com.example.comicslibrary.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.comicslibrary.viewmodel.LibraryApiViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.comicslibrary.AttributionText
import com.example.comicslibrary.CharacterImage
import com.example.comicslibrary.Destination
import com.example.comicslibrary.model.CharactersApiResponse
import com.example.comicslibrary.model.api.NetworkResult
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.lazy.items



@Composable
fun LibraryScreen(
    navController: NavHostController,
    vm: LibraryApiViewModel=hiltViewModel(),
    paddingValues: PaddingValues

){
    val result by vm.result.collectAsState()
    val text by vm.queryText.collectAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
      OutlinedTextField(
          value = text,
          onValueChange = vm::onQueryUpdate,
          label = {
            Text(
                text = "Character search"
            )
          },
          placeholder = {
              Text(
                  text="Characters"
              )
          },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
      )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result){
                is NetworkResult.Initial ->{
                    Text(
                        text = "Search for a character"
                    )
                }

                is NetworkResult.Success ->{
                    ShowCharactersList(result, navController)
                }

                is NetworkResult.Loading ->{
                    CircularProgressIndicator()
                }

                is NetworkResult.Error ->{
                    Text(
                        text = "Error: ${result.message}"
                    )
                }
            }
        }
    }

}

@Composable
fun ShowCharactersList(
    result: NetworkResult<CharactersApiResponse>,
    navController: NavHostController
){
    val context = LocalContext.current
    result.data?.data?.results?.let{
        characters ->
        LazyColumn(
            modifier = Modifier.background(Color.LightGray),
            verticalArrangement = Arrangement.Top
            ) {
            result.data.attributionText?.let{
                item{
                    AttributionText(text = it)
                }
            }

            items(characters){ character ->
                val imageUrl = character.thumbnail?.path+ "." + character.thumbnail?.extension
                val title = character.name
                val description = character.description
                val id = character.id


                Column(
                   modifier = Modifier
                       .padding(4.dp)
                       .clip(RoundedCornerShape(5.dp))
                       .background(Color.White)
                       .fillMaxWidth()
                       .wrapContentHeight()
                       .clickable {
                           if (id != null) {
                               navController.navigate(Destination.CharacterDetail.createRoute(id))
                           } else {
                               Toast.makeText(context, "Character id is null", Toast.LENGTH_SHORT).show()
                           }
                       }
                ) {

                    Row (modifier = Modifier.fillMaxWidth()){

                        CharacterImage(
                            url = imageUrl,
                            modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp)

                        )

                        Column  (modifier = Modifier.padding(4.dp)){
                            Text(
                                text = title?:"", fontWeight = FontWeight.Bold, fontSize = 20.sp
                            )
                        }

                    }

                    Text(
                        text = description?:"", maxLines = 4, fontSize = 14.sp
                    )
                }
            }
        }
    }
}