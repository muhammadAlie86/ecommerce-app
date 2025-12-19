package com.arranet.payajabisnis.ppob.component.tooltip

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ecommerce.R
import com.example.ecommerce.view.component.responsiveTextSize
import com.example.ecommerce.view.theme.White

@Composable
fun TooltipContent(
    modifier: Modifier,
    tooltipText: Int,
) {
    TooltipPopup(
        modifier = modifier,
        requesterView = {
            Icon(
                modifier = it,
                painter = painterResource(id = R.drawable.ic_info_error),
                contentDescription = "TooltipPopup",
                tint = Red,
            )
        },
        tooltipContent = {
            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(vertical = 8.dp),
                text = stringResource(tooltipText),
                style = TextStyle.Default
                    .responsiveTextSize(baseFontSize = 3f)
                    .copy(
                        color = White,
                        textAlign = TextAlign.Center,
                    ),
            )
        }
    )
}