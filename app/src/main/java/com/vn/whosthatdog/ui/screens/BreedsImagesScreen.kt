package com.vn.whosthatdog.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.vn.whosthatdog.ui.components.LoadingErrorPage
import com.vn.whosthatdog.viewmodel.BreedsImagesUiState
import com.vn.whosthatdog.viewmodel.BreedsImagesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsImagesScreen(breed: String, subBreed: String?) {
    val breedsImagesViewModel: BreedsImagesViewModel = viewModel()
    if (subBreed != null && subBreed != "") {
        breedsImagesViewModel.loadSubBreedImages(breed, subBreed)
    } else {
        breedsImagesViewModel.loadBreedImages(breed)

    }
    val breedName:String = (subBreed ?: breed).replaceFirstChar { it.uppercase() }
    val breedsImagesUiState by breedsImagesViewModel.breedsImagesUiState.collectAsState(initial = BreedsImagesUiState.Loading)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        breedName,
                        style = MaterialTheme.typography.displayMedium,
                    )
                }
            )
        },
        content = { innerPadding ->
            Surface(Modifier.padding(innerPadding)) {
                when (breedsImagesUiState) {
                    is BreedsImagesUiState.Loading -> Surface(modifier = Modifier.fillMaxSize()) {
                        LoadingErrorPage("Loading...")
                    }

                    is BreedsImagesUiState.Error -> Surface(modifier = Modifier.fillMaxSize()) {
                        LoadingErrorPage("Error")
                    }

                    is BreedsImagesUiState.Success -> GridView((breedsImagesUiState as BreedsImagesUiState.Success).breedImages)
                }
            }

        }
    )
}

@Composable
private fun GridView(breedImages: ArrayList<String>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(breedImages) { breedImage ->
            Image(
                painter = rememberAsyncImagePainter(breedImage),
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(300.dp)
                    .padding(4.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
            )
        }
    }
}