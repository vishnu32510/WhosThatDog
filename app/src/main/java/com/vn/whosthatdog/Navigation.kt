package com.vn.whosthatdog

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vn.whosthatdog.ui.screens.HomeScreen
import com.vn.whosthatdog.ui.login.LoginScreen
import com.vn.whosthatdog.ui.screens.BreedsImagesScreen
import com.vn.whosthatdog.ui.screens.BreedsScreen
import com.vn.whosthatdog.ui.signup.SignupScreen

sealed class Route {
    data class LoginScreen(val name: String = "Login") : Route()
    data class SignupScreen(val name: String = "SignUp") : Route()
    data class HomeScreen(val name: String = "Home") : Route()
    data class BreedsScreen(val name: String = "Breeds") : Route()
    data class BreedImagesScreen(val name: String = "BreedsImages", val breed: String, val subBreed: String?) : Route(){
        fun createRoute() = "BreedsImages/$breed/${subBreed}"
    }
}

@Composable
fun MyNavigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Route.LoginScreen().name) {
        composable(route = Route.LoginScreen().name) {
            LoginScreen(
                onSignUpClick = {
                    navHostController.navigateToSingleTop(Route.SignupScreen().name)
                },
                onLoginClick = {
                    navHostController.navigateToSingleTop(Route.HomeScreen().name)
                }
            )
        }
        composable(route = Route.SignupScreen().name) {
            SignupScreen(
                onSignupClick = {
                    navHostController.navigate(Route.HomeScreen().name)
                },
                onLoginClick = {
                    navHostController.navigateToSingleTop(Route.LoginScreen().name)
                }
            )
        }
        composable(route = Route.HomeScreen().name) {
            HomeScreen(
                onExploreClick = {
                    navHostController.navigate(Route.BreedsScreen().name)
                }
            )
        }
        composable(route = Route.BreedsScreen().name) {
            BreedsScreen { breed, subBreed ->
                navHostController.navigate(
//                    Route.BreedImagesScreen.createRoute(breed, subBreed)
                    Route.BreedImagesScreen(breed = breed, subBreed = subBreed).createRoute()                )
            }
        }
        composable(
//            route = Route.BreedImagesScreen(breed = "", subBreed = "").name,
            route = "BreedsImages/{breed}/{subBreed}",
            arguments = listOf(
                navArgument("breed") { type = NavType.StringType },
                navArgument("subBreed") { type = NavType.StringType; nullable = true }
            )
            ) {
//                backStackEntry ->
//            val breed = backStackEntry.arguments?.getString("breed") ?: ""
//            val subBreedRaw = backStackEntry.arguments?.getString("subBreed")
//            val subBreed = if (subBreedRaw == "") null else subBreedRaw
                backStackEntry ->
            val breed = backStackEntry.arguments?.getString("breed") ?: ""
            val subBreed = backStackEntry.arguments?.getString("subBreed")
            BreedsImagesScreen(breed = breed, subBreed = subBreed)
        }
    }
}

fun NavController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState()
        }
        launchSingleTop = true
        restoreState = true
    }
}