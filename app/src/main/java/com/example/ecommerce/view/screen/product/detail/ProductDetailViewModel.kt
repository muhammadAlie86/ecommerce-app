package com.example.ecommerce.view.screen.product.detail

import androidx.lifecycle.SavedStateHandle
import com.example.ecommerce.core.data.remote.models.common.CartItemUIModel
import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.remote.models.request.ProductItemRequest
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.usecase.cart.AddCartUseCase
import com.example.ecommerce.domain.usecase.cart.DeleteCartUseCase
import com.example.ecommerce.domain.usecase.cart.GetCartUserUseCase
import com.example.ecommerce.domain.usecase.cart.GetLocalCartCountUseCase
import com.example.ecommerce.domain.usecase.product.GetProductUseCase
import com.example.ecommerce.libraries.base.mvi.ErrorState
import com.example.ecommerce.libraries.base.mvi.IViewState
import com.example.ecommerce.libraries.base.mvi.LoadingState
import com.example.ecommerce.libraries.base.mvi.MviViewModel
import com.example.ecommerce.libraries.utils.ConstantsMessage.PRODUCT_EMPTY
import com.example.ecommerce.libraries.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getProductUseCase: GetProductUseCase,
    private val addCartUseCase: AddCartUseCase,
    private val getCartUserUseCase: GetCartUserUseCase,
    private val dataStoreManager: DataStoreManager,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val getLocalCartCountUseCase: GetLocalCartCountUseCase,
) : MviViewModel<HomeState>(){
    val productArgs = ProductDetailArgs(saveStateHandle)

    init {
        getUserData()
        observeCartBadge()
        getProduct(productArgs.productId)
    }
    private fun observeCartBadge() = safeLaunch {
        getLocalCartCountUseCase().collect { count ->
            setState { currentState.copy(cartItemCount = count) }
        }
    }
    fun getUserData() = safeLaunch {
        val userId = dataStoreManager.getUserId.first()

        setState { currentState.copy(userId = userId) }


    }

    fun getCartUser(userId : Int) = safeLaunch {
        val params = GetCartUserUseCase.Params(userId)
        execute(getCartUserUseCase(params)){ cartList ->
            if (cartList.isNotEmpty()) {
                val badgeCount = cartList.sumOf { cart ->
                    cart.products.sumOf { it.quantity }
                }
                setState {
                    currentState.copy(cartItemCount = badgeCount)
                }

            }
            else{
                handleError(Throwable(PRODUCT_EMPTY))
            }
        }
    }

    fun checkout() = safeLaunch {
        val params = DeleteCartUseCase.Params(cartId = 1, isCheckout = true)
        execute(deleteCartUseCase(params)) {
            setState { currentState.copy(product = null, isSuccess = true) }
        }
    }



    fun addProductToCart(product: Product) = safeLaunch {
        val productRequests = listOf(
            ProductItemRequest(
                productId = product.id,
                quantity = 1
            )
        )

        val requestBody = CartRequest(
            userId = currentState.userId,
            date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
            products = productRequests
        )
        val params = AddCartUseCase.Params(requestBody)
        execute(addCartUseCase(params)) {
            setState { currentState.copy(cartItemCount = currentState.cartItemCount + 1) }
        }
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
    val uiItems: List<CartItemUIModel> = emptyList(),
    val productList : List<Product> = emptyList(),
    val cartList: List<Cart> = emptyList(),
    val product : Product? = null,
    val cartItemCount : Int= 0,
    val userId : Int = -1,
    val isSuccess: Boolean = false
) : IViewState