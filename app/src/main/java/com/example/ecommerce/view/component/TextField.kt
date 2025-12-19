package com.example.ecommerce.view.component
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.arranet.payajabisnis.ppob.component.tooltip.TooltipContent
import com.example.ecommerce.view.theme.Black
import com.example.ecommerce.view.theme.PoppinsSemiBold
import com.example.ecommerce.view.theme.darker_grey
import com.example.ecommerce.view.theme.grey_40
import com.example.ecommerce.view.theme.grey_60

@Composable
fun TextFieldCommon(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    placeholder: Int,
    title: Int,
    isError: Boolean = false,
    supportingText: Int,
    isBold: Boolean = false,
    isPasswordField: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 15.dp),
            text = stringResource(title),
            style = TextStyle.Default.responsiveTextSize(
                baseFontSize = 3.4f,
                color = if (isBold) Black else grey_40,
                fontFamily = PoppinsSemiBold
            ),
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 15.dp),
            value = value,
            onValueChange = onValueChange,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            visualTransformation = if (isPasswordField && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            textStyle = TextStyle.Default.responsiveTextSize(
                baseFontSize = 3.5f,
                color = grey_60
            ),
            trailingIcon = {
                if (isError) {
                    TooltipContent(
                        modifier = Modifier.wrapContentHeight(),
                        tooltipText = supportingText
                    )
                } else if (isPasswordField) {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null, tint = grey_60)
                    }
                }
            },
            shape = RoundedCornerShape(15.dp),
            isError = isError,
            placeholder = {
                Text(
                    text = stringResource(placeholder),
                    style = TextStyle.Default.responsiveTextSize(
                        baseFontSize = 3f,
                        color = grey_60
                    )
                )
            },
            colors = OutlinedTextFieldDefaults.colors().copy(
                disabledIndicatorColor = darker_grey,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

