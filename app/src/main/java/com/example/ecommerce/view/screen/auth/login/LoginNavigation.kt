package com.example.ecommerce.view.screen.auth.login

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ecommerce.view.screen.auth.register.navigateToRegister
import com.example.ecommerce.view.screen.home.navigateToHome


const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin() {
    try {

        val route = LOGIN_ROUTE
        this.navigate(route) {
            popUpTo(graph.findStartDestination().id){
                inclusive = true
            }
        }
    }
    catch (e : Exception){
        e.printStackTrace()
    }
}

fun NavGraphBuilder.loginNavigation(
    navController: NavController
)
{
    composable(
        route = LOGIN_ROUTE,

        )
    {
        LoginScreen(
            onNavigateToRegister = navController::navigateToRegister,
            onNavigateToHome = navController::navigateToHome
        )
    }
}

