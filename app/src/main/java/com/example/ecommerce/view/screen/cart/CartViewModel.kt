package com.example.ecommerce.view.screen.cart

import androidx.compose.runtime.remember
import com.example.ecommerce.core.data.remote.models.common.CartItemUIModel
import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.remote.models.request.ProductItemRequest
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.domain.model.Product
import com.example.ecommerce.domain.usecase.cart.AddCartUseCase
import com.example.ecommerce.domain.usecase.cart.DeleteCartUseCase
import com.example.ecommerce.domain.usecase.cart.GetCartUserUseCase
import com.example.ecommerce.domain.usecase.cart.UpdateCartUseCase
import com.example.ecommerce.domain.usecase.product.GetAllProductUseCase
import com.example.ecommerce.domain.usecase.product.GetLocalProductsUseCase
import com.example.ecommerce.domain.usecase.product.UpdateCartProductUseCase
import com.example.ecommerce.libraries.base.mvi.ErrorState
import com.example.ecommerce.libraries.base.mvi.IViewState
import com.example.ecommerce.libraries.base.mvi.LoadingState
import com.example.ecommerce.libraries.base.mvi.MviViewModel
import com.example.ecommerce.libraries.utils.DataStoreManager
import com.example.ecommerce.libraries.utils.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUserUseCase: GetCartUserUseCase,
    private val getAllProductUseCase: GetAllProductUseCase,
    private val dataStoreManager: DataStoreManager,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val updateCartProductUseCase: UpdateCartProductUseCase,
    private val getLocalProductsUseCase: GetLocalProductsUseCase,
) : MviViewModel<CartState>() {

    init {
        getUserData()
        observeLocalProducts()
    }

    fun loadInitialData(userId: Int) {
        getAllProduct()
        getCartUser(userId)
    }
    fun getUserData() = safeLaunch {
        val userId = dataStoreManager.getUserId.first()

        setState { currentState.copy(userId = userId) }

    }

    private fun getAllProduct() = safeLaunch {
        execute(getAllProductUseCase(Unit)) { products ->
            if (products.isNotEmpty()) {
                setState { currentState.copy(productList = products) }
                checkAndMapData()
            } else {
                handleError(Throwable("PRODUCT_EMPTY"))
            }
        }
    }
    private fun observeLocalProducts() = safeLaunch {
        getLocalProductsUseCase().collect { products ->
            if (products.isNotEmpty()) {
                setState { currentState.copy(productList = products) }
            }
        }
    }

    private fun getCartUser(userId: Int) = safeLaunch {
        val params = GetCartUserUseCase.Params(userId)
        execute(getCartUserUseCase(params)) { cartList ->
            if (cartList.isNotEmpty()) {
                setState { currentState.copy(cartList = cartList) }
                checkAndMapData()
            } else {
                handleError(Throwable("CART_EMPTY"))
            }
        }
    }
    fun deleteProductFromCart(userId: Int, productId: Int) = safeLaunch {
        val params = DeleteCartUseCase.Params(userId)

        execute(deleteCartUseCase(params)) { response ->
            val currentList = currentState.cartList

            val updatedList = currentList.filter { it.id != productId }

            if (updatedList.isNotEmpty()) {
                setState {
                    currentState.copy(
                        cartList = updatedList,
                        isDialogVisible = false, // Tutup dialog
                        selectedProductId = null  // Reset ID
                    )
                }
            } else {
                setState {
                    currentState.copy(
                        cartList = emptyList(),
                        isDialogVisible = false,
                        selectedProductId = null
                    )
                }
                handleError(Throwable("CART_EMPTY"))
            }
        }
    }
    fun onSameProductActionTriggered(item: CartItemUIModel) = safeLaunch {

        execute(getAllProductUseCase(Unit)){ product ->
            if (product.isNotEmpty()) {
                val products = product.distinctBy { it.category }
                setState {
                    copy(
                        selectedProductToReplace = item.productId,
                        similarProducts = products,
                        showSheet = true
                    )
                }
            }
            else{
                handleError(Throwable("PRODUCT_EMPTY"))
            }
        }
    }
    fun replaceProductInCart(oldProductId: Int, newProduct: Product) = safeLaunch {
        val currentItems = currentState.uiItems

        val updatedProductsRequest = currentItems.map {
            if (it.productId == oldProductId) {
                ProductItemRequest(newProduct.id, 1)
            } else {
                ProductItemRequest(it.productId, it.quantity)
            }
        }

        val params = UpdateCartProductUseCase.Params(
            cartId = 1,
            request = CartRequest(
                userId = currentState.userId,
                date = DateUtil.getCurrentDate(),
                products = updatedProductsRequest
            )
        )

        execute(updateCartProductUseCase(params)) {
            // Berhasil
        }
    }
    fun updateQuantity(productId: Int, newQuantity: Int) = safeLaunch {
        val oldList = currentState.uiItems

        setState {
            currentState.copy(
                uiItems = oldList.map {
                    if (it.productId == productId) it.copy(quantity = newQuantity) else it
                }
            )
        }

        val requestBody = CartRequest(
            userId = currentState.userId,
            date = DateUtil.getCurrentDate(),
            products = listOf(ProductItemRequest(productId, newQuantity))
        )

        val params = UpdateCartUseCase.Params(cartId = 1, request = requestBody)
        execute(updateCartUseCase(params)) {

        }
    }
    fun checkout() = safeLaunch {
        val allItems = currentState.uiItems
        val itemsToBuy = allItems.filter { it.isSelected }
        val itemsToKeep = allItems.filter { !it.isSelected }

        if (itemsToBuy.isEmpty()) return@safeLaunch

        if (itemsToKeep.isEmpty()) {
            val params = DeleteCartUseCase.Params(cartId = 1, isCheckout = true)
            execute(deleteCartUseCase(params)) {
                setState { currentState.copy(isSuccess = true) }
            }
        } else {
            val requestBody = CartRequest(
                userId = currentState.userId,
                date = DateUtil.getCurrentDate(),
                products = itemsToKeep.map { ProductItemRequest(it.productId, it.quantity) }
            )

            val params = UpdateCartUseCase.Params(cartId = 1, request = requestBody)
            execute(updateCartUseCase(params)) {
                setState { currentState.copy(isSuccess = true) }
            }
        }
    }

    private fun checkAndMapData() {
        val products = currentState.productList
        val carts = currentState.cartList

        if (products.isNotEmpty() && carts.isNotEmpty()) {

            val allCartProducts = carts.flatMap { it.products }

            val mappedItems = allCartProducts.mapNotNull { cartItem ->
                products.find { it.id == cartItem.id }?.let { detail ->
                    CartItemUIModel(
                        productId = detail.id,
                        title = detail.title,
                        price = detail.price,
                        imageUrl = detail.imageUrl,
                        quantity = cartItem.quantity,
                        category = detail.category,
                        isSelected = false
                    )
                }
            }

            setState { currentState.copy(uiItems = mappedItems) }
        }
    }

    fun onProductCheckedChange(productId: Int, isSelected: Boolean) {
        setState {
            currentState.copy(uiItems = uiItems.map {
                if (it.productId == productId) it.copy(isSelected = isSelected) else it
            })
        }
    }



    fun onToggleAll(isSelected: Boolean) {
        setState {
            currentState.copy(uiItems = uiItems.map { it.copy(isSelected = isSelected) })
        }
    }
    fun onShowDeleteDialog(productId: Int) {
        setState {
            currentState.copy(
                isDialogVisible = true,
                selectedProductId = productId
            )
        }
    }

    fun onDismissDialog() {
        setState {
            currentState.copy(
                isDialogVisible = false,
                selectedProductId = null
            )
        }
    }
    override fun createInitialState(): CartState = CartState()

    override fun createInitialStateError(): ErrorState = ErrorState()

    override fun createInitialLoadingState(): LoadingState = LoadingState()
}
data class CartState(
    val uiItems: List<CartItemUIModel> = emptyList(),
    val productList : List<Product> = emptyList(),
    val cartList: List<Cart> = emptyList(),
    val userId: Int = -1,
    val isDialogVisible : Boolean = false,
    val selectedProductId: Int? = null,
    val isSuccess: Boolean = false,
    val showSimilarSheet: Boolean = false,
    val selectedProductToReplace: Int? = null,
    val similarProducts: List<Product> = emptyList(),
    val showSheet: Boolean = false
) : IViewState {
    val totalPrice: Double = uiItems.filter { it.isSelected }.sumOf { it.price * it.quantity }
    val isAllSelected: Boolean = uiItems.isNotEmpty() && uiItems.all { it.isSelected }
    val selectedCount: Int = uiItems.count { it.isSelected }
}