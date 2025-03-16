package com.vn.whosthatdog.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.whosthatdog.services.RetrofitClient
import com.vn.whosthatdog.utils.capitalizeFirstLetter
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data class Success(val imageURl: String) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

class HomeViewModel() : ViewModel() {

    private val _homeUiState = mutableStateOf<HomeUiState>(HomeUiState.Loading)
    val homeUiState: State<HomeUiState> = _homeUiState

    init {
        _homeUiState.value = HomeUiState.Loading
        fetchRandomImage()
    }

    fun showAnswer(): String{
        if (homeUiState.value !is HomeUiState.Success){
            return ""
        }
        val st = (homeUiState.value as HomeUiState.Success).imageURl
        val part = st.replace("-"," ").split(("/"))

        return part[part.size - 2].lowercase().capitalizeFirstLetter()
    }

    fun checkAnswer(answer: String?): Boolean{
        if (answer == null){
            return false
        }
        val st = (homeUiState.value as HomeUiState.Success).imageURl
        val part = st.replace("-"," ").split(("/"))

        return part[part.size - 2].lowercase() == answer.lowercase()
    }


    fun fetchRandomImage() {
        _homeUiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                val res = RetrofitClient.apiServices.getRandomImage()

                if (res.message != null  && res.status == "success"){
                    _homeUiState.value = HomeUiState.Success(imageURl = res.message)
                }
                else{
                    _homeUiState.value = HomeUiState.Error
                }
            } catch (e: Exception) {
                _homeUiState.value = HomeUiState.Error
            }

        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setTestHomeUiState(state: HomeUiState) {
        _homeUiState.value = state
    }
}