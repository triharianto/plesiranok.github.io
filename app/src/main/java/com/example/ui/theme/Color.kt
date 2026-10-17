package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// =========================================================================
// 5-Color Travel Palette from User Specification:
// 1. Berry / Deep Wine Magenta: #8C425D
// 2. Indigo / Slate Periwinkle Blue: #606BA6
// 3. Golden Yellow / Marigold: #F2B705
// 4. Tangerine / Vibrant Orange: #F27405
// 5. Coral / Sunset Vermillion: #F25A38
// =========================================================================

// Column 1: #8C425D (Berry / Plum)
val PaletteBerry = Color(0xFF8C425D)
val PaletteBerryDark = Color(0xFF5A2033)
val PaletteBerryLight = Color(0xFFFBF0F4)
val PaletteBerryContainer = Color(0xFFF4DCE4)

// Column 2: #606BA6 (Indigo / Slate Blue)
val PaletteIndigo = Color(0xFF606BA6)
val PaletteIndigoDark = Color(0xFF2C3669)
val PaletteIndigoLight = Color(0xFFEEF0F9)
val PaletteIndigoContainer = Color(0xFFDCE0F3)

// Column 3: #F2B705 (Golden Amber / Warm Yellow)
val PaletteYellow = Color(0xFFF2B705)
val PaletteYellowDark = Color(0xFF997200)
val PaletteYellowLight = Color(0xFFFEF9E6)
val PaletteYellowContainer = Color(0xFFFDF0C2)

// Column 4: #F27405 (Tangerine / Rich Orange)
val PaletteOrange = Color(0xFFF27405)
val PaletteOrangeDark = Color(0xFF9E4800)
val PaletteOrangeLight = Color(0xFFFFF2E6)
val PaletteOrangeContainer = Color(0xFFFEE4CE)

// Column 5: #F25A38 (Coral / Sunset Vermillion)
val PaletteCoral = Color(0xFFF25A38)
val PaletteCoralDark = Color(0xFFA13016)
val PaletteCoralLight = Color(0xFFFFF1EE)
val PaletteCoralContainer = Color(0xFFFDDED7)

// Backward-compatible semantic bindings mapped to new palette
val TravelokaBlue = PaletteIndigo
val TravelokaBlueDark = PaletteIndigoDark
val TravelokaBlueLight = PaletteIndigoLight

val TravelokaOrange = PaletteOrange
val TravelokaOrangeDark = PaletteOrangeDark
val TravelokaOrangeLight = PaletteOrangeLight

val PastelTeal = PaletteIndigo
val PastelTealLight = PaletteIndigoLight

val PastelGreen = PaletteBerry
val PastelGreenLight = PaletteBerryLight

val PastelPurple = PaletteBerry
val PastelPurpleLight = PaletteBerryLight

val PastelPink = PaletteCoral
val PastelPinkLight = PaletteCoralLight

val PastelAmber = PaletteYellow
val PastelAmberLight = PaletteYellowLight

val SurfaceBg = Color(0xFFF8F9FC)
val CardBorder = Color(0xFFE2E8F0)
val TextPrimary = Color(0xFF1E293B)
val TextSecondary = Color(0xFF64748B)
val TextMuted = Color(0xFF94A3B8)
