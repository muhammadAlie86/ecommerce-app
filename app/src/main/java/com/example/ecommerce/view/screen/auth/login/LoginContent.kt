package com.example.ecommerce.view.screen.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ecommerce.R
import com.example.ecommerce.view.component.ButtonPrimary
import com.example.ecommerce.view.component.TextFieldCommon
import com.example.ecommerce.view.component.debounceClickHandler
import com.example.ecommerce.view.component.responsiveTextSize
import com.example.ecommerce.view.theme.PoppinsSemiBold

@Composable
fun LoginContent(
    valueUsername: String,
    onValueUsername: (String) -> Unit,
    valuePassword : String,
    onValuePassword : (String) -> Unit,
    isError: Boolean = false,
    enable : Boolean = false,
    onProcessClick : () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val debouncedClick = debounceClickHandler<Unit> {
        onProcessClick()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_app),
                contentDescription = "icon app",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp),
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.Center,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                text = stringResource(id = R.string.login_title),
                style = TextStyle.Default.responsiveTextSize(
                    baseFontSize = 3.5f
                ).copy(
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsSemiBold,
                )
            )
            TextFieldCommon(
                value = valueUsername,
                onValueChange = onValueUsername,
                isError = isError,
                supportingText = R.string.error_username,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                placeholder = R.string.input_username,
                title = R.string.username


            )
            TextFieldCommon(
                value = valuePassword,
                onValueChange = onValuePassword,
                isError = isError,
                supportingText = R.string.error_password,
                isPasswordField = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                placeholder = R.string.password,
                title = R.string.input_password

            )
            ButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp) ,
                text = stringResource(id = R.string.sign_in),
                onClick = { debouncedClick(Unit) },
                enable = enable
            )



        }
    }
}
