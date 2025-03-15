package com.vn.whosthatdog

import com.vn.whosthatdog.viewmodel.HomeUiState
import com.vn.whosthatdog.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)

class HomeViewModelTest{

    private lateinit var homeViewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(this.testDispatcher)
        homeViewModel = HomeViewModel(autoFetch = false)
        homeViewModel.setTestHomeUiState(HomeUiState.Success("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"))
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }


    @Test
    fun checkAnswer_nullAnswer_returnsFalse(){


        val result = homeViewModel.checkAnswer(null)

        assertFalse(result)
    }

    @Test
    fun checkAnswer_correctAnswer_returnsFalse(){
        val result = homeViewModel.checkAnswer("hound afghan")

        assertTrue(result)
    }

    @Test
    fun checkAnswer_wrongAnswer_returnsFalse(){
        val result = homeViewModel.checkAnswer("null")

        assertFalse(result)
    }
}