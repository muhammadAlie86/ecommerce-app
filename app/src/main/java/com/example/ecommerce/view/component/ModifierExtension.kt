package com.example.ecommerce.view.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TextStyle.responsiveTextSize(
    baseFontSize: Float = 4f,
    minFontSizeSp: Float = 8f,
    maxFontSizeSp: Float = 40f,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Start,
    fontFamily: FontFamily = FontFamily.Default
): TextStyle {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthDp = configuration.screenWidthDp

    val calculatedSp = (screenWidthDp * (baseFontSize / 100f))

    val finalFontSize = with(density) {
        calculatedSp.coerceIn(minFontSizeSp, maxFontSizeSp).sp
    }

    return this.copy(
        fontSize = finalFontSize,
        color = color,
        textAlign = textAlign,
        fontFamily = fontFamily
    )
}

@Composable
fun String.validationDigit(
    value : String,
    maxLength : Int = 50,
    isDigitOnly: Boolean = false,
    isLetterOrDigit: Boolean = false,
    isLetterAndWhiteSpace: Boolean = false,
): String {
    val newText = kotlin.text.StringBuilder().apply {
        for (char in value) {
            if (
                (isDigitOnly && char.isDigit()) ||
                (isLetterOrDigit && (char.isLetterOrDigit() || char.isWhitespace())) ||
                (isLetterAndWhiteSpace && (char.isLetter() || char.isWhitespace()))
                )
            {
                if (length < maxLength) {
                    append(char)
                }
            }
        }
    }.toString()

    return newText

}
@Composable
fun Modifier.shimmerEffect(): Modifier {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_rect"
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        ),
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    return this.background(brush)
}

