package com.example.ecommerce.view.component
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ecommerce.view.theme.Black
import com.example.ecommerce.view.theme.PoppinsSemiBold
import com.example.ecommerce.view.theme.White
import com.example.ecommerce.view.theme.grey


@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enable : Boolean
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = grey
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 15.dp,
            focusedElevation = 10.dp
        ),
        shape = RoundedCornerShape(24.dp),
        onClick = onClick,
        enabled = enable
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = text,
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 3.5f)
                .copy(
                    color = White,
                    textAlign = TextAlign.Center,
                    fontFamily = PoppinsSemiBold
                )
        )

    }

}


@Composable
fun ButtonCommon(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    background : ButtonColors,
    isEnable: Boolean = false,
    textColor : Color = Black
) {
    Button(
        modifier = modifier,
        colors = background,
        shape = RoundedCornerShape(24.dp),
        onClick = onClick,
        enabled = isEnable,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 15.dp,
            focusedElevation = 10.dp
        ),
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = label,
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 3.5f)
                .copy(
                    color = textColor,
                    textAlign = TextAlign.Center,
                    fontFamily = PoppinsSemiBold
                )
        )

    }

}
