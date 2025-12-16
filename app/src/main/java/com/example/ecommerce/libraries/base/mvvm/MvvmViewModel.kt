package com.example.ecommerce.libraries.base.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.libraries.network.DataState
import com.example.ecommerce.libraries.network.Failure
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

abstract class MvvmViewModel : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.tag("ViewModel-ExceptionHand").e(exception)
        handleError(exception)
    }
    private val DEFAULT_EXCEPTION_MESSAGE= "Permintaan gagal diproses, silahkan coba beberapa saat lagi"

    open fun handleError(message : Throwable) {}

    open fun resetError() {}

    open fun startLoading() {}

    open fun hideLoading() {}

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    protected suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { e ->

                if (e is Failure.UnAuthorizedException) return@catch
                handleError(Throwable(DEFAULT_EXCEPTION_MESSAGE))}
            .collect {
                completionHandler.invoke(it)
            }
    }
    protected suspend fun <T> callBalance(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { e ->
                if (e is Failure.UnAuthorizedException) return@catch
                handleError(Throwable(e.localizedMessage ?: DEFAULT_EXCEPTION_MESSAGE))}
            .collect {
                completionHandler.invoke(it)
            }
    }

    protected suspend fun <T> execute(
        callFlow: Flow<DataState<T>>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .onStart {
                startLoading()
            }
            .catch {e ->
                hideLoading()
                if (e is Failure.UnAuthorizedException) return@catch
                if (e is CancellationException) throw e
                handleError(Throwable(DEFAULT_EXCEPTION_MESSAGE))
            }
            .collect { state ->
                when (state) {
                    is DataState.Error -> {
                        hideLoading()
                        handleError(state.error)
                    }
                    is DataState.Success -> {
                        hideLoading()
                        completionHandler.invoke(state.result)
                    }
                }
            }
    }




}