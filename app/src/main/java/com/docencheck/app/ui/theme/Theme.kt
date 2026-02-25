package com.docencheck.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary          = PrimaryDarkBlue,
    onPrimary        = BackgroundWhite,
    secondary        = PrimaryMint,
    onSecondary      = PrimaryDarkBlue,
    background       = BackgroundWhite,
    onBackground     = TextDark,
    surface          = SurfaceGray,
    onSurface        = TextDark,
    error            = ErrorRed,
    onError          = BackgroundWhite
)

@Composable
fun DocenCheckTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = Typography,
        content     = content
    )
}
