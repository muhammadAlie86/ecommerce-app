package com.example.ecommerce.view.screen.home

import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.model.User
import com.example.ecommerce.domain.usecase.cart.GetCartUserUseCase
import com.example.ecommerce.domain.usecase.product.GetAllProductUseCase
import com.example.ecommerce.domain.usecase.user.GetUserUseCase
import com.example.ecommerce.libraries.base.mvi.ErrorState
import com.example.ecommerce.libraries.base.mvi.IViewState
import com.example.ecommerce.libraries.base.mvi.LoadingState
import com.example.ecommerce.libraries.base.mvi.MviViewModel
import com.example.ecommerce.libraries.utils.ConstantsMessage.DEFAULT_MESSAGE
import com.example.ecommerce.libraries.utils.ConstantsMessage.PRODUCT_EMPTY
import com.example.ecommerce.libraries.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductUseCase: GetAllProductUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getCartUserUseCase: GetCartUserUseCase,
    private val dataStoreManager: DataStoreManager
) : MviViewModel<HomeState>(){
    init {
        getUserData()
        getAllProduct()
    }

    fun getAllProduct() = safeLaunch {
        execute(getAllProductUseCase(Unit)){ product ->
            if (product.isNotEmpty()) {
                setState { currentState.copy(productList = product) }
            }
            else{
                handleError(Throwable(PRODUCT_EMPTY))
            }
        }
    }
    fun getUser(userId : Int) = safeLaunch {
        val params = GetUserUseCase.Params(userId)
        execute(getUserUseCase(params)){ user ->
            if (user.username.isNotEmpty()) {
                setState { currentState.copy(user = user) }
            }
            else{
                handleError(Throwable(DEFAULT_MESSAGE))
            }
        }
    }
    fun getChartUser(userId : Int) = safeLaunch {
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
    fun isSheetOpen(isSheetOpen: Boolean) = safeLaunch {
        setState { currentState.copy(isSheetOpen = isSheetOpen, user = if (!isSheetOpen) null else currentState.user ) }
    }
    fun getUserData() = safeLaunch {
        val userId = dataStoreManager.getUserId.first()
        val username = dataStoreManager.getUsername.first()

        setState { currentState.copy(userId = userId, username = username) }

        if (userId != -1) {
            getChartUser(userId)
        }
    }



    override fun createInitialState(): HomeState = HomeState()

    override fun createInitialStateError(): ErrorState = ErrorState()

    override fun createInitialLoadingState(): LoadingState = LoadingState()
}
data class HomeState(
    val productList : List<Product> = emptyList(),
    val user : User? = null,
    val isSheetOpen : Boolean = false,
    val username : String = "",
    val userId: Int = -1,
    val cartItemCount : Int= 0
) : IViewState