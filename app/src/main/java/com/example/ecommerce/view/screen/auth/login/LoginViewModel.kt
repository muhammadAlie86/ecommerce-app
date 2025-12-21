package com.example.ecommerce.view.screen.auth.login

import androidx.lifecycle.viewModelScope
import com.example.ecommerce.core.data.remote.models.common.JwtUserPayload
import com.example.ecommerce.core.data.remote.models.request.LoginRequest
import com.example.ecommerce.domain.usecase.auth.LoginUseCase
import com.example.ecommerce.libraries.base.mvi.ErrorState
import com.example.ecommerce.libraries.base.mvi.IViewState
import com.example.ecommerce.libraries.base.mvi.LoadingState
import com.example.ecommerce.libraries.base.mvi.MviViewModel
import com.example.ecommerce.libraries.utils.ConstantsMessage.INCORECT_AUTH
import com.example.ecommerce.libraries.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStoreManager: DataStoreManager
) :  MviViewModel<LoginState>(){

    fun valueUsername(username: String) {
        val maxLength = 20

        val filteredUsername = username.take(maxLength)

        setState {
            currentState.copy(username = filteredUsername)
        }

        isEnable()
    }

    fun valuePassword(password: String) {
        val maxLength = 20
        val filteredPassword = if (password.length > maxLength) {
            password.substring(0, maxLength)
        } else {
            password
        }

        setState {
            currentState.copy(password = filteredPassword)
        }

        isEnable()
    }



    private fun isEnable() {
        val state = currentState
        val isUsernameValid = state.username.isNotEmpty()
        val isPasswordValid = state.password.isNotEmpty()

        setState {
            currentState.copy(
                isEnable = isUsernameValid && isPasswordValid
            )
        }
    }
    fun validateInput(username: String,password: String,onProcessClick : () -> Unit) {
        if (username.isEmpty() || password.isEmpty()) {
            setState {
                currentState.copy(
                    isError = true
                )
            }
        }
        else{
            onProcessClick()
        }
    }
    fun login(username: String, password: String) = safeLaunch {
        val request = LoginRequest(username,password)
        val params = LoginUseCase.Params(request)
        execute(loginUseCase(params)){login ->
            if (login.token.isNotEmpty()) {
                val userPayload = decodeToken(login.token)


                userPayload?.let { data ->
                    viewModelScope.launch {
                        saveUserId(data.userId, data.username)
                        println("DEBUG: Berhasil simpan ID: ${data.userId}")
                    }
                    setState { currentState.copy(onNavigate = true) }
                }

            }
            else{
                handleError(Throwable(INCORECT_AUTH))
            }
        }
    }
    suspend fun saveUserId(userId: Int,username: String) {
        dataStoreManager.saveUserId(userId)
        dataStoreManager.saveUsername(username)
    }
    fun decodeToken(token: String): JwtUserPayload? {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return null

            val payload = String(android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE))
            val jsonObject = JSONObject(payload)

            JwtUserPayload(
                userId = jsonObject.getInt("sub"),
                username = jsonObject.getString("user")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun createInitialState(): LoginState = LoginState()
    override fun createInitialStateError(): ErrorState = ErrorState()
    override fun createInitialLoadingState(): LoadingState = LoadingState()

}
data class LoginState(
    val username: String = "",
    val password: String = "",
    var isEnable: Boolean = false,
    val isLabel : Boolean = false,
    val isError : Boolean = false,
    var onNavigate : Boolean = false,
) : IViewState
