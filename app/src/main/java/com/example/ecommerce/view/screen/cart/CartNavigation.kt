package com.example.ecommerce.view.screen.cart

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ecommerce.view.screen.home.HomeScreen
import com.example.ecommerce.view.screen.product.category.navigateToCategoryDetail
import com.example.ecommerce.view.screen.product.detail.navigateToProductDetail


const val CART_ROUTE = "cart_route"

fun NavController.navigateToCart() {
    try {

        val route = CART_ROUTE
        this.navigate(route)

    }
    catch (e : Exception){
        e.printStackTrace()
    }
}
fun NavGraphBuilder.cartNavigation(
    navController: NavController
)
{
    composable(
        route = CART_ROUTE,
    )
    {
        CartScreen()

    }


}
