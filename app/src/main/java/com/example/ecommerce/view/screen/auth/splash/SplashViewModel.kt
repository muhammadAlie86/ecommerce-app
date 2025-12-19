package com.example.ecommerce.view.screen.auth.splash

import com.example.ecommerce.libraries.base.mvi.ErrorState
import com.example.ecommerce.libraries.base.mvi.IViewState
import com.example.ecommerce.libraries.base.mvi.LoadingState
import com.example.ecommerce.libraries.base.mvi.MviViewModel
import com.example.ecommerce.libraries.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : MviViewModel<SplashState>() {

    init {
        isLoggedIn()
    }
    private fun isLoggedIn() = safeLaunch{
        val isLogin = dataStoreManager.isLoggedIn.firstOrNull()
        setState {
            currentState.copy(
                isLoggedIn = isLogin
            )
        }
    }


    override fun createInitialState(): SplashState = SplashState()

    override fun createInitialStateError(): ErrorState = ErrorState()

    override fun createInitialLoadingState(): LoadingState = LoadingState()

}
data class SplashState(
    val isLoggedIn : Boolean? = null
) : IViewState