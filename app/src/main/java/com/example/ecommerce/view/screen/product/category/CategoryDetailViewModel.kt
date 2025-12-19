package com.example.ecommerce.view.screen.product.category

import androidx.lifecycle.SavedStateHandle
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.usecase.product.GetAllProductCategoryUseCase
import com.example.ecommerce.domain.usecase.product.GetAllProductUseCase
import com.example.ecommerce.libraries.base.mvi.ErrorState
import com.example.ecommerce.libraries.base.mvi.IViewState
import com.example.ecommerce.libraries.base.mvi.LoadingState
import com.example.ecommerce.libraries.base.mvi.MviViewModel
import com.example.ecommerce.libraries.utils.ConstantsMessage.PRODUCT_EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getAllProductCategoryUseCase: GetAllProductCategoryUseCase
) : MviViewModel<HomeState>(){
    val categoryArgs = CategoryDetailArgs(saveStateHandle)

    init {
        getAllProductCategory(categoryArgs.category)
    }


    fun getAllProductCategory(category : String) = safeLaunch {
        val request = GetAllProductCategoryUseCase.Params(category)
        execute(getAllProductCategoryUseCase(request)){ product ->
            if (product.isNotEmpty()) {
                setState { currentState.copy(productList = product) }
            }
            else{
                handleError(Throwable(PRODUCT_EMPTY))
            }
        }
    }


    override fun createInitialState(): HomeState = HomeState()

    override fun createInitialStateError(): ErrorState = ErrorState()

    override fun createInitialLoadingState(): LoadingState = LoadingState()
}
data class HomeState(
    val productList : List<Product> = emptyList()
) : IViewState