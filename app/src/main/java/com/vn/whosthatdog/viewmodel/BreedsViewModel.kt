package com.vn.whosthatdog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.whosthatdog.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface BreedsUiState {
    data object Loading : BreedsUiState
    data object Error : BreedsUiState
    data class Success(val breeds: Map<String, ArrayList<String>>) : BreedsUiState
}


class BreedsViewModel : ViewModel() {

    private val _breedUiState = MutableStateFlow<BreedsUiState>(BreedsUiState.Loading)
    val breedsUiState: StateFlow<BreedsUiState> = _breedUiState


    init {
        loadBreedsList()
    }

    private fun loadBreedsList() {
        viewModelScope.launch {
            _breedUiState.value = BreedsUiState.Loading
            try {
                val res = RetrofitClient.apiServices.getBreedsList()
                if (res.message != null && res.status == "success") {
                    Log.d("BreedsLis", "List ${res.message}")
                    _breedUiState.value = BreedsUiState.Success(breeds = res.message)
                } else {
                    _breedUiState.value = BreedsUiState.Error
                }
            } catch (e: Exception) {
                _breedUiState.value = BreedsUiState.Error
            }
        }
    }
}