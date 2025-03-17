package com.vn.whosthatdog.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.vn.whosthatdog.ui.components.CustomTextField
import com.vn.whosthatdog.ui.components.HeaderText
import com.vn.whosthatdog.ui.components.NavigationBottomBar
import com.vn.whosthatdog.ui.components.ShowImage
import com.vn.whosthatdog.viewmodel.HomeUiState
import com.vn.whosthatdog.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onExploreClick: () -> Unit) {
    val context = LocalContext.current
    val (answer, onAnswerChange) = rememberSaveable { mutableStateOf("") }
    val homeViewModel: HomeViewModel = viewModel()
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title =
                { HeaderText(modifier = Modifier, text = "Home Screen") },
                actions = {
                    IconButton(onClick = onExploreClick) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Button(
                onClick = { homeViewModel.fetchRandomImage() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text("Refresh")
            }
        },
        bottomBar = {
            NavigationBottomBar()
        },
//        floatingActionButtonPosition = {  },
//        containerColor = {  },
//        contentColor = {  },
//        contentWindowInsets = {  },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Who's that Dog?",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                )
                Spacer(Modifier.height(8.dp))
                when (homeViewModel.homeUiState.value) {
                    is HomeUiState.Loading -> Surface(modifier = Modifier.fillMaxSize()) {
                        LoadingErrorImage("Loading...")
                    }

                    is HomeUiState.Error -> Surface(modifier = Modifier.fillMaxSize()) {
                        LoadingErrorImage("Error")
                    }

                    is HomeUiState.Success -> ImageView(imageUrl = (homeViewModel.homeUiState.value as HomeUiState.Success).imageURl)
                }

                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(
                    value = answer,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    onValueChange = onAnswerChange,
                    labelText = "Breed",
                    leadingIcon = Icons.Filled.Info,
//                        keyBoardType = TODO(),
//                        visualTransformation = TODO()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = {
                        val res: Boolean = homeViewModel.checkAnswer(answer)
                        if (res) {
                            Toast.makeText(context, "You are right!!", Toast.LENGTH_SHORT).show()
                            homeViewModel.fetchRandomImage()
                            onAnswerChange("")
                        } else {
                            Toast.makeText(context, "Better luck Next time!!", Toast.LENGTH_SHORT)
                                .show()
                            onAnswerChange("")
                        }
                    }) {
                        Text("Check")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        val res: String = homeViewModel.showAnswer()
                        onAnswerChange(res)
                    }) {
                        Text("Show Answer")
                    }
                }
            }
        }
    )
}

@Composable
private fun LoadingErrorImage(text: String) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(horizontal = 50.dp),
//                    color = MaterialTheme.colorScheme.primary,
        shadowElevation = 10.dp,
//                    contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(text, modifier = Modifier)
    }
}

@Composable
fun ImageView(imageUrl: String?) {
    val painter = rememberAsyncImagePainter(imageUrl)

    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(horizontal = 50.dp),
//                    color = MaterialTheme.colorScheme.primary,
        shadowElevation = 20.dp,
//                    contentColor = MaterialTheme.colorScheme.primary,
    )
    {
        ShowImage(painter)
    }
}
