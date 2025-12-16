package com.example.ecommerce.libraries.base.mvi

import com.example.ecommerce.libraries.base.mvvm.MvvmViewModel
import com.example.ecommerce.libraries.network.Failure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import timber.log.Timber

interface IViewState

abstract class MviViewModel<State : IViewState> : MvvmViewModel() {


    private val initialState: State by lazy { createInitialState() }

    private val initialStateError: ErrorState by lazy { createInitialStateError() }

    private val initialLoadingState: LoadingState by lazy { createInitialLoadingState() }
    abstract fun createInitialState(): State
    abstract fun createInitialStateError(): ErrorState
    abstract fun createInitialLoadingState(): LoadingState

    val currentState: State get() = uiState.value

    val errorState: ErrorState get() = uiStateError.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState

    private val _uiStateError: MutableStateFlow<ErrorState> = MutableStateFlow(initialStateError)
    val uiStateError: StateFlow<ErrorState> = _uiStateError

    private val _uiLoadingState: MutableStateFlow<LoadingState> = MutableStateFlow(initialLoadingState)
    val uiLoadingState: StateFlow<LoadingState> = _uiLoadingState

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
        Timber.tag("NewState").d(newState.toString())
    }
    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        if (exception == Failure.UnAuthorizedException) return
        _uiStateError.update {
            it.copy(isError = true, errorMessage = exception)
        }
    }
    fun Throwable.isUnauthorized(): Boolean =
        this is HttpException && this.code() == 401


    override fun resetError() {
        super.resetError()
        _uiStateError.update {
            it.copy(isError = false)
        }
    }



    override fun startLoading() {
        super.startLoading()
        _uiLoadingState.update {
            it.copy(isLoading = true)
        }

    }
    override fun hideLoading() {
        super.hideLoading()
        _uiLoadingState.update {
            it.copy(isLoading = false)
        }

    }



}
data class ErrorState(
    var isError : Boolean = false,
    val errorMessage: Throwable? = null
) : IViewState

data class LoadingState(
    var isLoading : Boolean = false
) : IViewState