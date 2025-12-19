package com.example.ecommerce.view.screen.auth.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ecommerce.view.screen.auth.login.navigateToLogin
import com.example.ecommerce.view.screen.home.navigateToHome

const val SPLASH_ROUTE = "splash_route"


fun NavGraphBuilder.splashNavigation(
    navController: NavController
)
{
    composable(
        route = SPLASH_ROUTE,
    )
    {

        SplashScreen(
            onNavigateToLogin = navController::navigateToLogin,
            onNavigateToHome =navController::navigateToHome
        )

    }
}

