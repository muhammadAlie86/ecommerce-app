package com.example.ecommerce.view.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecommerce.view.theme.Black
import com.example.ecommerce.view.theme.PoppinsRegular

@Composable
fun TextStyle.responsiveTextSize(
    baseFontSizeSp: Float = 14f,
    screenWidthFraction: Float = 1.0f,
    minFontSizeSp: Float = 13f,
    maxFontSizeSp: Float = 18f,
    color: Color = Black,
    textAlign: TextAlign = TextAlign.Start,
    fontFamily: FontFamily = PoppinsRegular
): TextStyle {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthDp = configuration.screenWidthDp.dp
    val fractionedScreenWidthDp = screenWidthDp * screenWidthFraction

    val textSizeSp = with(density) {
        (fractionedScreenWidthDp / baseFontSizeSp).toSp()
    }.value.coerceIn(minFontSizeSp, maxFontSizeSp).sp

    return this.copy(
        fontSize = textSizeSp,
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

