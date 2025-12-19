package com.example.ecommerce.view.screen.product.category

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ecommerce.view.screen.product.detail.navigateToProductDetail
import timber.log.Timber


const val CATEGORY_DETAIL = "category_detail_route"
const val CATEGORY_DETAIL_ARGS = "category_detail_args"

class CategoryDetailArgs(val category: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                try {
                    checkNotNull( savedStateHandle.get<String>(CATEGORY_DETAIL_ARGS)  )
                }
                catch (e : Exception){
                    Timber.tag(CATEGORY_DETAIL_ARGS).d("category object not found")
                    ""
                }
            )
}

fun NavController.navigateToCategoryDetail(category : String?) {
    try {

        val categoryArgValue = if (category.isNullOrEmpty()) "" else category
        val route = "$CATEGORY_DETAIL?$CATEGORY_DETAIL_ARGS=$categoryArgValue"
        this.navigate(route)

    }
    catch (e : Exception){
        e.printStackTrace()
    }
}
fun NavGraphBuilder.categoryDetailNavigation(
    navController: NavController
)
{
    composable(
        route = "$CATEGORY_DETAIL?$CATEGORY_DETAIL_ARGS={$CATEGORY_DETAIL_ARGS}",
    )
    {
        CategoryDetailScreen(
            onNavigateToProductDetail = navController::navigateToProductDetail,
            onPopBackStack = navController::popBackStack
        )

    }


}
