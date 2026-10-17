package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppStrings
import com.example.data.model.AppLanguage
import com.example.data.model.PlaceCategory
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

data class NavGridButtonItem(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector,
    val pastelBg: Color,
    val accentColor: Color,
    val onClick: () -> Unit
)

@Composable
fun HomeNavigationGrid(
    language: AppLanguage,
    onCategoryClick: (PlaceCategory) -> Unit,
    onTransportClick: () -> Unit,
    onAgendaClick: () -> Unit,
    onMapClick: () -> Unit,
    onTranslatorClick: () -> Unit,
    onEmergencyClick: () -> Unit,
    onSavedClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 12 Navigation Services mapped in 3 rows of 4 columns:
    // 1. Hotel & Penginapan
    // 2. Tempat Wisata
    // 3. Wisata Hiburan (Wahana, Waterpark, Ecopark, Museum)
    // 4. Transportasi (Darat, Laut, Udara digabung)
    // 5. Travel & Sewa Kendaraan (Daftar Mobil, Motor, Travel)
    // 6. Restoran & Kuliner
    // 7. Toko Oleh-oleh
    // 8. Agenda Wisata (Kalender)
    // 9. Peta Daerah (Denah Wisata, Resto, Oleh-oleh, dll)
    // 10. Penerjemah Bahasa Daerah
    // 11. Kontak Darurat
    // 12. Favorit / Tersimpan
    val navButtons = listOf(
        NavGridButtonItem(
            id = "btn_hotel",
            title = AppStrings.getCategoryTitle(PlaceCategory.HOTEL, language),
            icon = Icons.Default.Hotel,
            pastelBg = PaletteIndigoLight,
            accentColor = PaletteIndigo,
            onClick = { onCategoryClick(PlaceCategory.HOTEL) }
        ),
        NavGridButtonItem(
            id = "btn_tourism",
            title = AppStrings.getCategoryTitle(PlaceCategory.TOURISM, language),
            icon = Icons.Default.Landscape,
            pastelBg = PaletteBerryLight,
            accentColor = PaletteBerry,
            onClick = { onCategoryClick(PlaceCategory.TOURISM) }
        ),
        NavGridButtonItem(
            id = "btn_entertainment",
            title = AppStrings.getCategoryTitle(PlaceCategory.ENTERTAINMENT, language),
            subtitle = "Wahana & Park",
            icon = Icons.Default.Attractions,
            pastelBg = PaletteCoralLight,
            accentColor = PaletteCoral,
            onClick = { onCategoryClick(PlaceCategory.ENTERTAINMENT) }
        ),
        NavGridButtonItem(
            id = "btn_transport_all",
            title = "Transportasi",
            subtitle = "Darat, Laut, Udara",
            icon = Icons.Default.DirectionsBus,
            pastelBg = PaletteOrangeLight,
            accentColor = PaletteOrange,
            onClick = onTransportClick
        ),
        NavGridButtonItem(
            id = "btn_rental_travel",
            title = AppStrings.getCategoryTitle(PlaceCategory.TRANSPORT, language),
            subtitle = "Mobil & Motor",
            icon = Icons.Default.DirectionsCar,
            pastelBg = PaletteYellowLight,
            accentColor = PaletteOrange,
            onClick = { onCategoryClick(PlaceCategory.TRANSPORT) }
        ),
        NavGridButtonItem(
            id = "btn_culinary",
            title = AppStrings.getCategoryTitle(PlaceCategory.CULINARY, language),
            icon = Icons.Default.Fastfood,
            pastelBg = PaletteCoralLight,
            accentColor = PaletteCoral,
            onClick = { onCategoryClick(PlaceCategory.CULINARY) }
        ),
        NavGridButtonItem(
            id = "btn_souvenir",
            title = AppStrings.getCategoryTitle(PlaceCategory.SOUVENIR, language),
            icon = Icons.Default.ShoppingBag,
            pastelBg = PaletteYellowLight,
            accentColor = PaletteYellow,
            onClick = { onCategoryClick(PlaceCategory.SOUVENIR) }
        ),
        NavGridButtonItem(
            id = "btn_agenda",
            title = AppStrings.getNavAgenda(language),
            icon = Icons.Default.CalendarMonth,
            pastelBg = PaletteIndigoLight,
            accentColor = PaletteIndigo,
            onClick = onAgendaClick
        ),
        NavGridButtonItem(
            id = "btn_map",
            title = AppStrings.getNavMap(language),
            subtitle = "Denah & Lokasi",
            icon = Icons.Default.Map,
            pastelBg = PaletteIndigoLight,
            accentColor = PaletteIndigo,
            onClick = onMapClick
        ),
        NavGridButtonItem(
            id = "btn_translator",
            title = AppStrings.getNavTranslator(language),
            icon = Icons.Default.Translate,
            pastelBg = PaletteBerryLight,
            accentColor = PaletteBerry,
            onClick = onTranslatorClick
        ),
        NavGridButtonItem(
            id = "btn_emergency",
            title = AppStrings.getNavEmergency(language),
            icon = Icons.Default.Emergency,
            pastelBg = PaletteCoralLight,
            accentColor = PaletteCoral,
            onClick = onEmergencyClick
        ),
        NavGridButtonItem(
            id = "btn_saved",
            title = when (language) {
                AppLanguage.INDONESIA -> "Tersimpan"
                AppLanguage.ENGLISH -> "Saved"
                AppLanguage.TURKISH -> "Kayıtlı"
                AppLanguage.RUSSIAN -> "Сохраненное"
                AppLanguage.JAPANESE -> "保存済み"
                AppLanguage.CHINESE -> "已收藏"
            },
            icon = Icons.Default.Bookmark,
            pastelBg = PaletteOrangeLight,
            accentColor = PaletteOrange,
            onClick = onSavedClick
        )
    )

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("home_navigation_grid_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 8.dp)
        ) {
            // Header Mini Layanan Utama
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Layanan Informasi Wisata",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1E293B),
                        fontSize = 13.sp
                    )
                )
                Text(
                    text = "12 Layanan Lengkap",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = PaletteIndigo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.5.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Chunk buttons into 3 rows of 4 items each
            navButtons.chunked(4).forEachIndexed { rowIndex, rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Top
                ) {
                    rowItems.forEach { btn ->
                        NavGridButton(item = btn)
                    }
                }
                if (rowIndex < 2) {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@Composable
private fun NavGridButton(item: NavGridButtonItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(76.dp)
            .clickable(onClick = item.onClick)
            .testTag(item.id)
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(item.pastelBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = item.accentColor,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                lineHeight = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E293B),
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (!item.subtitle.isNullOrBlank()) {
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    color = item.accentColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
