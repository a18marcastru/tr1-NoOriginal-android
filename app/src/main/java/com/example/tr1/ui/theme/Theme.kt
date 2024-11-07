// Theme.kt
package com.example.tr1.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = LightOrange,
    secondary = LightRed,
    tertiary = LightYellow,
    background = LightGreen,
    surface = LightGreen,
//    onPrimary = Color.Blue,
//    onSecondary = Color.Blue,
//    onTertiary = Color.Blue,
//    onBackground = Color.White,
//    onSurface = Color.Blue,
)

private val LightColorScheme = lightColorScheme(
    primary = LightGreen,
    secondary = LightRed,
    tertiary = LightYellow,
    background = LightGreen,
    surface = LightGreen,
    onPrimary = Color.Blue,
    onSecondary = Color.Blue,
    onTertiary = Color.Blue,
    onBackground = Color.Blue,
    onSurface = Color.Blue,
)

@Composable
fun TR1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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
