package com.example.ecommerce.view.screen.auth.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ecommerce.BuildConfig
import com.example.ecommerce.R
import com.example.ecommerce.view.component.ContainerBody
import com.example.ecommerce.view.component.responsiveTextSize
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ContainerBody {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {


            Image(
                painter = painterResource(id = R.drawable.ic_logo_app),
                contentDescription = "logo app",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.Center)
                    .size(200.dp)
            )

        }

    }


    LaunchedEffect(uiState.isLoggedIn) {
        delay(3000)
        if (uiState.isLoggedIn == true){
            onNavigateToHome()
        }
        if (uiState.isLoggedIn == false){
            //onNavigateToHome()
           onNavigateToLogin()
        }
    }



}