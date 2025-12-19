package com.example.ecommerce.view.screen.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import timber.log.Timber


const val PRODUCT_DETAIL = "product_detail_route"
const val PRODUCT_DETAIL_ARGS = "product_detail_args"

class ProductDetailArgs(val productId: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                try {
                    checkNotNull( savedStateHandle.get<Int>(PRODUCT_DETAIL_ARGS)  )
                }
                catch (e : Exception){
                    Timber.tag(PRODUCT_DETAIL_ARGS).d("PRODUCT object not found")
                    -1
                }
            )
}

fun NavController.navigateToProductDetail(productId : Int?) {
    try {

        val route = "$PRODUCT_DETAIL?$PRODUCT_DETAIL_ARGS=$productId"
        this.navigate(route)

    }
    catch (e : Exception){
        e.printStackTrace()
    }
}
fun NavGraphBuilder.productDetailNavigation(
    navController: NavController
)
{
    composable(
        route = "$PRODUCT_DETAIL?$PRODUCT_DETAIL_ARGS={$PRODUCT_DETAIL_ARGS}",
        arguments = listOf(
            navArgument(PRODUCT_DETAIL_ARGS) {
                type = NavType.IntType
                defaultValue = -1
                nullable = false
            }
        )
    )
    {
        ProductDetailScreen(
            popBackStack = navController::popBackStack
        )

    }


}
