package com.example.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ui.theme.PaletteBerry
import com.example.ui.theme.PaletteBerryLight
import com.example.ui.theme.PaletteCoral
import com.example.ui.theme.PaletteCoralLight
import com.example.ui.theme.PaletteIndigo
import com.example.ui.theme.PaletteIndigoLight
import com.example.ui.theme.PaletteOrange
import com.example.ui.theme.PaletteOrangeLight
import com.example.ui.theme.PaletteYellow
import com.example.ui.theme.PaletteYellowLight

enum class PlaceCategory(
    val id: String,
    val titleId: String,
    val icon: ImageVector,
    val pastelBg: Color,
    val accentColor: Color
) {
    HOTEL(
        id = "hotel",
        titleId = "Hotel & Penginapan",
        icon = Icons.Default.Hotel,
        pastelBg = PaletteIndigoLight,
        accentColor = PaletteIndigo
    ),
    TOURISM(
        id = "tourism",
        titleId = "Tempat Wisata",
        icon = Icons.Default.Landscape,
        pastelBg = PaletteBerryLight,
        accentColor = PaletteBerry
    ),
    ENTERTAINMENT(
        id = "entertainment",
        titleId = "Wisata Hiburan",
        icon = Icons.Default.Attractions,
        pastelBg = PaletteCoralLight,
        accentColor = PaletteCoral
    ),
    TRANSPORT(
        id = "transport",
        titleId = "Travel & Sewa Kendaraan",
        icon = Icons.Default.DirectionsCar,
        pastelBg = PaletteOrangeLight,
        accentColor = PaletteOrange
    ),
    CULINARY(
        id = "culinary",
        titleId = "Restoran & Kuliner",
        icon = Icons.Default.Fastfood,
        pastelBg = PaletteYellowLight,
        accentColor = PaletteYellow
    ),
    SOUVENIR(
        id = "souvenir",
        titleId = "Toko Oleh-oleh",
        icon = Icons.Default.ShoppingBag,
        pastelBg = PaletteIndigoLight,
        accentColor = PaletteIndigo
    );

    companion object {
        fun fromId(id: String): PlaceCategory {
            return entries.find { it.id.equals(id, ignoreCase = true) } ?: HOTEL
        }
    }
}
