
package com.example.ecommerce.view.component
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ecommerce.R
import com.example.ecommerce.view.theme.Black
import com.example.ecommerce.view.theme.PoppinsSemiBold

@ExperimentalMaterial3Api
@Composable
fun ToolbarWithoutNavIcon(
    @StringRes titleResId: Int,
) {
    TopAppBar(
        title = {
            Text(
                stringResource(titleResId),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleSmall
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary
            ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithNavIcon(
    titleResId: String,
    pressOnBack: () -> Unit,
    isClose: Boolean =false
) {
    TopAppBar(
        title = {
            Text(
                text = titleResId,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp),
                style = TextStyle.Default
                    .responsiveTextSize(baseFontSizeSp = 17f, screenWidthFraction = 0.8f)
                    .copy(
                        color = Black,
                        textAlign = TextAlign.Center,
                        fontFamily = PoppinsSemiBold
                    )
            )
        },
        navigationIcon = {
            Icon(
                painterResource(R.drawable.ic_back),
                contentDescription = null,
                tint = Black,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { pressOnBack() }
            )
        },
        colors =  TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),

    )
}

