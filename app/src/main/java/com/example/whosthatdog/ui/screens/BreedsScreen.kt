package com.example.whosthatdog.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whosthatdog.ui.components.LoadingErrorPage
import com.example.whosthatdog.utils.capitalizeFirstLetter
import com.example.whosthatdog.viewmodel.BreedsUiState
import com.example.whosthatdog.viewmodel.BreedsViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.toList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsScreen(onBreedClick: (breed: String, subBreed: String?) -> Unit) {
    val breedsViewModel: BreedsViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "List of Dog Breends",
                        style = MaterialTheme.typography.titleLarge,
//                        modifier = Modifier.padding(5.dp)
                    )
                },
            )
        },
        content = {
            val breedsUiState by breedsViewModel.breedsUiState.collectAsState(initial = BreedsUiState.Loading)
            Surface(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                when (breedsUiState) {
                    is BreedsUiState.Loading -> Surface(modifier = Modifier.fillMaxSize()) {
                        LoadingErrorPage("Loading...")
                    }

                    is BreedsUiState.Error -> Surface(modifier = Modifier.fillMaxSize()) {
                        LoadingErrorPage("Error")
                    }

                    is BreedsUiState.Success ->
                        ItemsList(
                            (breedsUiState as BreedsUiState.Success).breeds,
                            breedsViewModel,
                            onBreedClick = onBreedClick
                        )

                }
            }


        }
    )

}

@Composable
fun ItemsList(
    breeds: Map<String, ArrayList<String>>,
    breedsViewModel: BreedsViewModel,
    onBreedClick: (breed: String, subBreed: String?) -> Unit
) {
    LazyColumn(
        Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        breeds.forEach { (breed, subBreeds) ->
            item {
                Column() {
                    ItemRow(
                        "Breed: ${breed.capitalizeFirstLetter()}",
                        modifier = Modifier.clickable {
                            onBreedClick(breed, null)
                        })
                    subBreeds.forEach { subBreed ->
                        ItemRow(
                            "Sub Breed: ${subBreed.capitalizeFirstLetter()}",
                            modifier = Modifier.clickable {
                                onBreedClick(breed, subBreed)
                            })
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemRow(breed: String, modifier: Modifier) {
    Card(
        modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = breed,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}


