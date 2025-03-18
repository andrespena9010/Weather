package com.exaple.weather.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LightColorScheme = lightColorScheme(
    primary = Color(0xFFD1B3FF),
    onPrimary = Color.Black,
    secondary = Color(0xFFE6CCFF),
    onSecondary = Color.Black,
    tertiary = Color(0xFFF2E5FF),
    onTertiary = Color.Black,
    background = Color(0xFFF9F8F7),
    onBackground = Color.Black,
    surface = Color(0xFFEDE7F6),
    onSurface = Color.Black,
    error = Color(0xFF8B0000),
    onError = Color.White,
    outline = Color(0xFF4B0082)
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9A7DFF),
    onPrimary = Color.White,
    secondary = Color(0xFF7E57C2),
    onSecondary = Color.White,
    tertiary = Color(0xFFB39DDB),
    background = Color(0xFF1C1C1E),
    onBackground = Color.White,
    surface = Color(0xFF2A2A2E),
    onSurface = Color.White,
    error = Color(0xFFFF4444),
    onError = Color.Black,
    outline = Color(0xFFD8BFD8)
)

@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}