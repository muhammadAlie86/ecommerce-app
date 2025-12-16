package com.example.ecommerce.view.component
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
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
    placeholder: String,
    title : String,
    isError: Boolean = false,
    supportingText: String,
    isBold: Boolean = false
) {

    Column (
        modifier = Modifier.fillMaxWidth()

    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 15.dp),
            text = title,

            style = TextStyle.Default
                .responsiveTextSize(
                    baseFontSizeSp = 14f,
                    screenWidthFraction = 0.6f,
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
            textStyle = TextStyle.Default
                .responsiveTextSize(
                    baseFontSizeSp = 16f,
                    screenWidthFraction = 0.8f,
                    color = grey_60
                ),
            trailingIcon = {
                if (isError) {

                    TooltipContent(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(),
                        tooltipText = supportingText
                    )
                }

            },
            shape = RoundedCornerShape(15.dp),
            isError = isError,
            supportingText = {

            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle.Default
                        .responsiveTextSize(baseFontSizeSp = 16f, screenWidthFraction = 0.7f,
                            color = grey_60
                        )

                )
            },
            colors = OutlinedTextFieldDefaults.colors().copy(disabledIndicatorColor = darker_grey, focusedIndicatorColor = MaterialTheme.colorScheme.primary)

        )
    }
}

