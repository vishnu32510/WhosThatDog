package com.vn.whosthatdog

import com.vn.whosthatdog.models.RandomImageModel
import com.vn.whosthatdog.services.ApiServices
import com.vn.whosthatdog.services.RetrofitClient
import com.vn.whosthatdog.viewmodel.HomeUiState
import com.vn.whosthatdog.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

//    private val apiService: ApiServices = mockk()

    private fun setTestHomeUiStateSuccess() {
        homeViewModel.setTestHomeUiState(HomeUiState.Success("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"))

    }

    private fun setTestHomeUiStateError() {
        homeViewModel.setTestHomeUiState(HomeUiState.Error)

    }

    private fun setTestHomeUiStateLoading() {
        homeViewModel.setTestHomeUiState(HomeUiState.Loading)

    }

    @Before
    fun setUp() {
        Dispatchers.setMain(this.testDispatcher)
        homeViewModel = HomeViewModel()
//        homeViewModel.setApiServices(
//            apiServices = apiService
//        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

//    @Test
//    fun fetchRandomImage_success() {
//        // Arrange: Mock API call
//        coEvery { apiService.getRandomImage() } returns RandomImageModel(status = "success", message = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg")
//
//        // Act: Call the ViewModel function
//        homeViewModel.fetchRandomImage()
//
//        // Assert: Verify state change
//        assert(homeViewModel.homeUiState.value is HomeUiState.Error)
//
//        // Verify API was called
//        coVerify { apiService.getRandomImage() }
//    }

    @Test
    fun homeViewModel_Initialization_State() {
        val homeUiState = homeViewModel.homeUiState

        assertTrue(homeUiState.value is HomeUiState.Loading)
    }

    @Test
    fun checkAnswer_nullAnswer_returnsFalse() {
        setTestHomeUiStateSuccess()
        val result = homeViewModel.checkAnswer(null)

        assertFalse(result)
    }

    @Test
    fun checkAnswer_correctAnswer_returnsFalse() {
        setTestHomeUiStateSuccess()
        val result = homeViewModel.checkAnswer("hound afghan")

        assertTrue(result)
    }

    @Test
    fun checkAnswer_wrongAnswer_returnsFalse() {
        setTestHomeUiStateSuccess()
        val result = homeViewModel.checkAnswer("null")

        assertFalse(result)
    }

    @Test
    fun showAnswer_SuccessState() {
        setTestHomeUiStateSuccess()
        val result = homeViewModel.showAnswer()

        assertEquals("Hound afghan", result)
    }

    @Test
    fun showAnswer_ErrorState() {
        setTestHomeUiStateError()
        val result = homeViewModel.showAnswer()

        assertEquals("", result)
    }

    @Test
    fun showAnswer_LoadingState() {
        setTestHomeUiStateLoading()
        val result = homeViewModel.showAnswer()

        assertEquals("", result)
    }
}