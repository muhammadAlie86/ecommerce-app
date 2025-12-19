package com.example.ecommerce.view.screen.auth.register

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ecommerce.view.screen.auth.login.navigateToLogin


const val REGISTER_ROUTE = "register_route"

fun NavController.navigateToRegister() {
    try {
        val route = "register_route"
        this.navigate(route)
    }
    catch (e : Exception){
        e.printStackTrace()
    }
}
fun NavGraphBuilder.registerNavigation(
    navController: NavController
) {
    composable(
        route = REGISTER_ROUTE,
    ) {
        RegisterScreen(
            onNavigateToLogin = navController::navigateToLogin

        )
    }
}


