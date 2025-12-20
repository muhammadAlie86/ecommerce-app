package com.example.ecommerce.view.component

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce.R
import com.example.ecommerce.view.theme.Black
import com.example.ecommerce.view.theme.PoppinsRegular

@Composable
fun DialogCommon(
    @StringRes label: Int = 0,
    visible: Boolean,
    onDismiss: () -> Unit,
    action: () -> Unit,
    message: String,
) {

    AnimatedVisibility(visible = visible, exit = ExitTransition.None) {

        AlertDialog(
            modifier = Modifier.padding(10.dp),
            onDismissRequest = { onDismiss() },
            confirmButton = {
                ButtonPrimary(
                    text = stringResource(id = R.string.ok),
                    onClick = { onDismiss() },
                    enable = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                )
            },
            title = {
                Text(
                    text = stringResource(id = label),
                    style = TextStyle.Default
                        .responsiveTextSize(baseFontSize = 3.5f)
                        .copy(
                            color = Black,
                            textAlign = TextAlign.Start,
                            fontFamily = PoppinsRegular
                        ),
                )
            },
            text = {
                Text(
                    text = message,
                    style = TextStyle.Default
                        .responsiveTextSize(baseFontSize = 3.5f)
                        .copy(
                            color = Black,
                            textAlign = TextAlign.Start,
                            fontFamily = PoppinsRegular
                        ),
                )
            }
        )
    }
}

@Composable
fun DialogConfirmation(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onYesAction: () -> Unit,
    onNoAction: () -> Unit,
    title: String
) {

    AnimatedVisibility(visible = visible, exit = ExitTransition.None) {
        AlertDialog(
            modifier = Modifier.padding(10.dp),
            onDismissRequest = {},
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly

                ) {


                    ButtonCommon(
                        label = "Ya",
                        onClick = {
                            onYesAction()
                        },
                        isEnable = true,
                        background = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            .padding(2.dp)
                            .weight(1f)
                    )
                    ButtonCommon(
                        label = "Tidak",
                        onClick = {
                            onNoAction()
                        },
                        isEnable = true,
                        background = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.surface,
                        ),
                        modifier = Modifier
                            .padding(2.dp)
                            .weight(1f)
                    )
                }
            },

            title = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.information),
                        style = TextStyle.Default
                            .responsiveTextSize(baseFontSize = 3.5f)
                            .copy(
                                color = Black,
                            ),
                        modifier = Modifier
                            .clickable { onYesAction() }
                    )

                    Text(
                        text = title,
                        style = TextStyle.Default
                            .responsiveTextSize(baseFontSize = 3.5f)  .copy(
                                color = Black,
                            ),
                    )
                }
            }
        )
    }

}


