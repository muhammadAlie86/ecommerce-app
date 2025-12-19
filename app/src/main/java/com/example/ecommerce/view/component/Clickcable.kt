package com.example.ecommerce.view.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
inline fun <T> debounceClickHandler(
    debounceTime: Long = 300L,
    crossinline onDebouncedClick: (T) -> Unit
): (T) -> Unit {
    val coroutineScope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    return { parameter: T ->
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(debounceTime)
            onDebouncedClick(parameter)
        }
    }
}