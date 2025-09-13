package com.shatytskyi.munchcounter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.shatytskyi.munchcounter.ui.theme.MunchkinTheme

@Composable
fun MunchkinCustomDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MunchkinTheme.colors.background,
    header: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .systemBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(16.dp),
                color = containerColor
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Scrollable content (underneath header/footer)
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                            .padding(
                                top = if (header != null) 96.dp else 24.dp, // 80dp header height + 16dp spacing
                                bottom = if (footer != null) 96.dp else 24.dp // 80dp footer height + 16dp spacing
                            )
                            .padding(horizontal = 24.dp)
                    ) {
                        content()
                    }

                    // Header overlay with semi-transparent background
                    header?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter)
                                .background(containerColor.copy(alpha = 0.95f))
                        ) {
                            it()
                        }
                    }

                    // Footer overlay with semi-transparent background
                    footer?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(containerColor.copy(alpha = 0.95f))
                        ) {
                            it()
                        }
                    }
                }
            }
        }
    }
}
