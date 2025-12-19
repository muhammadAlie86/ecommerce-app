package com.example.ecommerce.view.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.ecommerce.view.screen.auth.login.loginNavigation
import com.example.ecommerce.view.screen.auth.register.registerNavigation
import com.example.ecommerce.view.screen.auth.splash.SPLASH_ROUTE
import com.example.ecommerce.view.screen.auth.splash.splashNavigation
import com.example.ecommerce.view.screen.cart.cartNavigation
import com.example.ecommerce.view.screen.home.homeNavigation
import com.example.ecommerce.view.screen.product.category.categoryDetailNavigation
import com.example.ecommerce.view.screen.product.detail.productDetailNavigation


@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_ROUTE,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(500)
            )
        }


    ){
        splashNavigation(navController = navController)
        loginNavigation(navController = navController)
        homeNavigation(navController = navController)
        registerNavigation(navController = navController)
        categoryDetailNavigation(navController = navController)
        productDetailNavigation(navController = navController)
        cartNavigation(navController = navController)


    }
}