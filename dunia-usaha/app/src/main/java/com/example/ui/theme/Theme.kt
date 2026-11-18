package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DuniaUsahaLightColorScheme = lightColorScheme(
    primary = SunAmber,
    onPrimary = Color.White,
    primaryContainer = SunGold,
    onPrimaryContainer = InkNavy,
    secondary = CoralPink,
    onSecondary = Color.White,
    secondaryContainer = RoseSoft,
    onSecondaryContainer = InkNavy,
    tertiary = MeadowGreen,
    onTertiary = Color.White,
    tertiaryContainer = MeadowMint,
    onTertiaryContainer = MeadowOutline,
    background = CreamBackground,
    onBackground = InkNavy,
    surface = CreamCard,
    onSurface = InkNavy,
    surfaceVariant = CreamWarm,
    onSurfaceVariant = InkMuted,
    outline = InkNavy
)

@Composable
fun DuniaUsahaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DuniaUsahaLightColorScheme,
        typography = DuniaUsahaTypography,
        content = content
    )
}
