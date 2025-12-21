package com.example.ecommerce.view.screen.cart

import androidx.compose.ui.graphics.TransformOrigin
import com.example.ecommerce.core.data.remote.models.common.CartItemUIModel
import com.example.ecommerce.core.data.remote.models.request.CartRequest
import com.example.ecommerce.core.data.remote.models.request.ProductItemRequest
import com.example.ecommerce.domain.model.Cart
import com.example.ecommerce.domain.model.Product
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
import timber.log.Timber
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
                handleError(Throwable("PRODUCT TIDAK TERSEDIA"))
            }
        }
    }
    private fun observeLocalProducts() = safeLaunch {
        getLocalProductsUseCase().collect { products ->
            if (products.isNotEmpty()) {
                setState { currentState.copy(productList = products) }
                checkAndMapData()
            }
        }
    }

    private fun getCartUser(userId: Int) = safeLaunch {
        val params = GetCartUserUseCase.Params(userId)
        execute(getCartUserUseCase(params)) { cartList ->
            val cartId = cartList.first().id
            if (cartList.isNotEmpty()) {
                setState { currentState.copy(cartList = cartList, cartId = cartId) }
                checkAndMapData()
            } else {
                handleError(Throwable("KERANJANG KOSONG"))
            }
        }
    }
    fun deleteProductFromCart(userId: Int, productId: Int) = safeLaunch {
        val params = DeleteCartUseCase.Params(userId)

        execute(deleteCartUseCase(params)) { response ->
            val currentCartList = currentState.cartList
            val updatedCartList = currentCartList.map { cart ->
                val remainingProducts = cart.products.filter { it.id != productId }
                cart.copy(products = remainingProducts)
            }.filter { it.products.isNotEmpty() }

            val updatedUiItems = currentState.uiItems.filter { it.productId != productId }

            setState {
                currentState.copy(
                    cartList = updatedCartList,
                    uiItems = updatedUiItems,
                    isDialogVisible = false,
                    selectedProductId = null
                )
            }

            if (updatedUiItems.isEmpty()) {
                handleError(Throwable("KERANJANG KOSONG"))
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
                handleError(Throwable("PRODUCT TIDAK TERSEDIA"))
            }
        }
    }
    fun replaceProductInCart(oldProductId: Int, newProduct: Product) = safeLaunch {
        val currentItems = currentState.uiItems

        val optimisticList = currentItems.map {
            if (it.productId == oldProductId) {
                it.copy(
                    productId = newProduct.id,
                    title = newProduct.title,
                    quantity = 1,
                    imageUrl = newProduct.imageUrl
                )
            } else it
        }

        setState { currentState.copy(uiItems = optimisticList) }

        val updatedProductsRequest = optimisticList.map {
            ProductItemRequest(it.productId, it.quantity)
        }
        val currentCartId = currentState.cartId
        val params = UpdateCartProductUseCase.Params(
            cartId = currentCartId,
            request = CartRequest(
                userId = currentState.userId,
                date = DateUtil.getCurrentDate(),
                products = updatedProductsRequest
            )
        )
        execute(updateCartProductUseCase(params)) {
            if (it.products.isNotEmpty()){
                handleError(Throwable("Berhasil Melakukan Perubahan"))
            }else{
                handleError(Throwable("Gagal Melakukan Perubahan"))
            }
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
        val currentCartId = currentState.cartId
        val params = UpdateCartUseCase.Params(cartId = currentCartId, request = requestBody)
        execute(updateCartUseCase(params)) {
            Timber.d("updateQuantity: $it")
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
                if (it.products.isNotEmpty()){
                    handleError(Throwable("Pembelian Berhasil"))
                    setState { currentState.copy(isSuccess = true) }
                }else{
                    handleError(Throwable("Gagal Melakukan Pembelian"))
                }
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

    fun onToggleSwipe(productId: Int, isSwiped: Boolean) {
        setState {
            val newSwipedIds = if (isSwiped) {
                swipedProductIds + productId
            } else {
                swipedProductIds - productId
            }
            copy(swipedProductIds = newSwipedIds)
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
    val cartId: Int = -1,
    val isDialogVisible : Boolean = false,
    val selectedProductId: Int? = null,
    val isSuccess: Boolean = false,
    val showSimilarSheet: Boolean = false,
    val selectedProductToReplace: Int? = null,
    val similarProducts: List<Product> = emptyList(),
    val showSheet: Boolean = false,
    val swipedProductIds: Set<Int> = emptySet(),
) : IViewState {
    val totalPrice: Double = uiItems.filter { it.isSelected }.sumOf { it.price * it.quantity }
    val isAllSelected: Boolean = uiItems.isNotEmpty() && uiItems.all { it.isSelected }
    val selectedCount: Int = uiItems.count { it.isSelected }
}