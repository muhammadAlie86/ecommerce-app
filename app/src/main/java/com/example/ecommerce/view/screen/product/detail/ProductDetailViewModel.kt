package com.example.ecommerce.view.screen.product.detail

import androidx.lifecycle.SavedStateHandle
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.usecase.product.GetAllProductCategoryUseCase
import com.example.ecommerce.domain.usecase.product.GetAllProductUseCase
import com.example.ecommerce.domain.usecase.product.GetProductUseCase
import com.example.ecommerce.libraries.base.mvi.ErrorState
import com.example.ecommerce.libraries.base.mvi.IViewState
import com.example.ecommerce.libraries.base.mvi.LoadingState
import com.example.ecommerce.libraries.base.mvi.MviViewModel
import com.example.ecommerce.libraries.utils.ConstantsMessage.PRODUCT_EMPTY
import com.example.ecommerce.view.screen.product.category.CategoryDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getProductUseCase: GetProductUseCase,
) : MviViewModel<HomeState>(){
    val productArgs = ProductDetailArgs(saveStateHandle)

    init {
        getProduct(productArgs.productId)
    }


    fun getProduct(productId : Int) = safeLaunch {
        val request = GetProductUseCase.Params(productId)
        execute(getProductUseCase(request)){ product ->
            setState { currentState.copy(product = product) }
        }
    }


    override fun createInitialState(): HomeState = HomeState()

    override fun createInitialStateError(): ErrorState = ErrorState()

    override fun createInitialLoadingState(): LoadingState = LoadingState()
}
data class HomeState(
    val product : Product? = null
) : IViewState