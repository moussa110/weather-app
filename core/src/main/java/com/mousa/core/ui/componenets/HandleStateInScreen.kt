package com.mousa.core.ui.componenets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mousa.core.domain.util.BaseState

@Composable
fun <T> HandleState(
    modifier: Modifier = Modifier,
    state: BaseState<T>,
    onRetry: () -> Unit = {},
    content: @Composable (T) -> Unit
) {
    when (state) {
        is BaseState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is BaseState.Failure -> {
            FailureScreen(
                error = state.errorMessage,
                onRetry = onRetry
            )
        }

        is BaseState.Success -> {
            content(state.data)
        }
    }
}