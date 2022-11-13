package com.sonu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sonu.core.domain.ProgressBarState
import com.sonu.core.domain.Queue
import com.sonu.core.domain.UIComponent

@Composable
fun DefaultScreenUI(
    queue: Queue<UIComponent> = Queue(mutableListOf()),
    onRemoveHeadFromQueue: () -> Unit,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            content()
            if (!queue.isEmpty()) {
                queue.peek()?.let { uiComponent ->
                    if (uiComponent is UIComponent.Dialog) {
                        GenericDialog(
                            title = uiComponent.title,
                            description = uiComponent.description,
                            onRemoveHeadFromQueue = onRemoveHeadFromQueue
                        )
                    }
                }
            }
            if (progressBarState is ProgressBarState.Loading) {
                CircularIndeterminateProgressBar()
            }
        }
    }

}