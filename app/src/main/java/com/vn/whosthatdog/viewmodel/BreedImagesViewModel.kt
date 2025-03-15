package com.vn.whosthatdog.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.whosthatdog.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface BreedsImagesUiState {
    data object Loading : BreedsImagesUiState
    data object Error : BreedsImagesUiState
    data class Success(val breedImages: ArrayList<String>) : BreedsImagesUiState
}


class BreedsImagesViewModel() : ViewModel() {

    private val _breedsImagesUiState =
        MutableStateFlow<BreedsImagesUiState>(BreedsImagesUiState.Loading)
    val breedsImagesUiState: StateFlow<BreedsImagesUiState> = _breedsImagesUiState

//    init {
//        if(subBreed != null){
//            loadSubBreedImages(breed,subBreed)
//        }else{
//        loadBreedImages(breed)
//
//        }
//    }

    fun loadBreedImages(breed: String) {
        viewModelScope.launch {
            _breedsImagesUiState.value = BreedsImagesUiState.Loading
            try {
                val res = RetrofitClient.apiServices.getImagesByBreed(breed)
                if (res.message != null && res.status == "success") {
                    _breedsImagesUiState.value = BreedsImagesUiState.Success(res.message)
                } else {
                    _breedsImagesUiState.value = BreedsImagesUiState.Error
                }
            } catch (e: Exception) {
                _breedsImagesUiState.value = BreedsImagesUiState.Error

            }
        }
    }

    fun loadSubBreedImages(breed: String, subBreed: String) {
        viewModelScope.launch {
            _breedsImagesUiState.value = BreedsImagesUiState.Loading
            try {
                val res = RetrofitClient.apiServices.getImagesBySubBreed(breed, subBreed)
                if (res.message != null && res.status == "success") {
                    _breedsImagesUiState.value = BreedsImagesUiState.Success(res.message)
                } else {
                    _breedsImagesUiState.value = BreedsImagesUiState.Error
                }
            } catch (e: Exception) {
                _breedsImagesUiState.value = BreedsImagesUiState.Error

            }
        }
    }
}