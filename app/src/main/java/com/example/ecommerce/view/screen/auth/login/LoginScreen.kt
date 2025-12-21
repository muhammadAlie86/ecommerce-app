package com.example.ecommerce.view.screen.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce.R
import com.example.ecommerce.libraries.utils.ConstantsMessage.DEFAULT_MESSAGE
import com.example.ecommerce.view.component.ContainerBody
import com.example.ecommerce.view.component.DialogCommon
import com.example.ecommerce.view.component.FullScreenLoading

@Composable
fun LoginScreen(
    onNavigateToHome : () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiErrorState by viewModel.uiStateError.collectAsStateWithLifecycle()
    val uiLoadingState by viewModel.uiLoadingState.collectAsStateWithLifecycle()


    ContainerBody {
        DialogCommon(
            label = R.string.information,
            visible = uiErrorState.isError,
            onDismiss = {
                viewModel.resetError()
            },
            action = {
                viewModel.resetError()
            },
            message = uiErrorState.errorMessage?.localizedMessage ?: DEFAULT_MESSAGE
        )

        FullScreenLoading(visible = uiLoadingState.isLoading)

        LoginContent(
            valueUsername = uiState.username,
            onValueUsername = {username ->
                viewModel.valueUsername(username)
            },
            valuePassword = uiState.password,
            onValuePassword = {password ->
                viewModel.valuePassword(password)
            },
            isError = uiState.isError,
            enable = uiState.isEnable,
            onProcessClick = {
                viewModel.validateInput(
                    uiState.username,
                    uiState.password
                ) {
                    viewModel.login(uiState.username, uiState.password)
                }
            }
        )
    }
    LaunchedEffect(uiState.onNavigate) {
        if (uiState.onNavigate){
            onNavigateToHome()
        }
    }
}