package com.example.ecommerce.view.screen.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ecommerce.view.screen.cart.navigateToCart
import com.example.ecommerce.view.screen.product.category.navigateToCategoryDetail
import com.example.ecommerce.view.screen.product.detail.navigateToProductDetail


const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome() {
    try {

        val route = HOME_ROUTE
        this.navigate(route){
            popUpTo(HOME_ROUTE){
                inclusive = true
            }
        }

    }
    catch (e : Exception){
        e.printStackTrace()
    }
}
fun NavGraphBuilder.homeNavigation(
    navController: NavController
)
{
    composable(
        route = HOME_ROUTE,
    )
    {
        HomeScreen(
            onNavigateToCategoryDetail = navController::navigateToCategoryDetail,
            onNavigateToProductDetail = navController::navigateToProductDetail,
            onNavigateToCart = navController::navigateToCart

        )

    }


}
