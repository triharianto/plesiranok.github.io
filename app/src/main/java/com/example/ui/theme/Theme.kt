package com.example.ui.theme

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

private val LightColorScheme = lightColorScheme(
    primary = PaletteIndigo,          // #606BA6
    onPrimary = Color.White,
    primaryContainer = PaletteIndigoContainer,
    onPrimaryContainer = PaletteIndigoDark,
    secondary = PaletteOrange,        // #F27405
    onSecondary = Color.White,
    secondaryContainer = PaletteOrangeContainer,
    onSecondaryContainer = PaletteOrangeDark,
    tertiary = PaletteBerry,          // #8C425D
    onTertiary = Color.White,
    tertiaryContainer = PaletteBerryContainer,
    onTertiaryContainer = PaletteBerryDark,
    background = SurfaceBg,
    onBackground = TextPrimary,
    surface = Color.White,
    onSurface = TextPrimary,
    surfaceVariant = PaletteIndigoLight,
    onSurfaceVariant = TextSecondary,
    outline = CardBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = PaletteIndigo,
    onPrimary = Color.White,
    primaryContainer = PaletteIndigoDark,
    onPrimaryContainer = PaletteIndigoLight,
    secondary = PaletteOrange,
    onSecondary = Color.White,
    secondaryContainer = PaletteOrangeDark,
    onSecondaryContainer = PaletteOrangeLight,
    tertiary = PaletteBerry,
    onTertiary = Color.White,
    tertiaryContainer = PaletteBerryDark,
    onTertiaryContainer = PaletteBerryLight,
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

@Composable
fun MyApplicationTheme(
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
