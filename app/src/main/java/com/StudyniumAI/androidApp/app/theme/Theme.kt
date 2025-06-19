package com.StudyniumAI.androidApp.app.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val LightColorSchemePixelDream = lightColorScheme(
    primary = carnationPink,
    onPrimary = onCarnationPink,
    secondary = fairyTale,
    onSecondary = onFairyTale,
    tertiary = uranianBlue,
    onTertiary = onUranianBlue,
    background = thistle,
    onBackground = onThistle,
    surface = lightSkyBlue,
    onSurface = onLightSkyBlue,
)

private val DarkColorSchemePixelDream = darkColorScheme(
    primary = darkCarnationPink,
    onPrimary = onDarkCarnationPink,
    secondary = darkFairyTale,
    onSecondary = onDarkFairyTale,
    tertiary = darkUranianBlue,
    onTertiary = onDarkUranianBlue,
    background = darkThistle,
    onBackground = onDarkThistle,
    surface = darkLightSkyBlue,
    onSurface = onDarkLightSkyBlue,
)

private val LightColorSchemeNeuralBloom = lightColorScheme(
    primary = tropicalIndigo,
    onPrimary = onTropicalIndigo,
    secondary = periwinkle,
    onSecondary = onPeriwinkle,
    tertiary = apricot,
    onTertiary = onApricot,
    background = ghostWhite,
    onBackground = onGhostWhite,
    surface = antiqueWhite,
    onSurface = onAntiqueWhite,
)

private val DarkColorSchemeNeuralBloom = darkColorScheme(
    primary = darkTropicalIndigo,
    onPrimary = onDarkTropicalIndigo,
    secondary = darkPeriwinkle,
    onSecondary = onDarkPeriwinkle,
    tertiary = darkApricot,
    onTertiary = onDarkApricot,
    background = darkGhostWhite,
    onBackground = onDarkGhostWhite,
    surface = darkAntiqueWhite,
    onSurface = onDarkAntiqueWhite,
)

@Composable
fun DynamicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorSchemePixelDream
        else -> LightColorSchemePixelDream
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun PixelDreamTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColorSchemePixelDream else LightColorSchemePixelDream

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun NeuralBloomTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColorSchemeNeuralBloom else LightColorSchemeNeuralBloom

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}