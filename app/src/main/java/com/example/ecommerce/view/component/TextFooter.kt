package com.example.ecommerce.view.component

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ecommerce.view.theme.PoppinsBold
import com.example.ecommerce.view.theme.PoppinsMedium
import com.example.ecommerce.view.theme.grey_600

@Composable
fun TextFooter(
    modifier: Modifier = Modifier,
    onNavigate : () -> Unit,
    label : Int,
    value : Int

){

    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier.padding(end = 3.dp),
            text = stringResource(label),
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 3.5f)
                .copy(
                    color = grey_600,
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsMedium
                ),
        )
        Text(
            modifier = Modifier.clickable {
                onNavigate()
            },
            text = stringResource(value),
            style = TextStyle.Default
                .responsiveTextSize(baseFontSize = 3.5f)
                .copy(
                    textAlign = TextAlign.Start,
                    fontFamily = PoppinsBold
                ),
        )
    }
}