package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.datasource.PlesiranDataSource
import com.example.data.localization.AppStrings
import com.example.data.model.AppLanguage
import com.example.data.model.DestinationRegion
import com.example.data.model.PlaceCategory
import com.example.data.model.PlaceItem
import com.example.ui.theme.PaletteBerry
import com.example.ui.theme.PaletteCoral
import com.example.ui.theme.PaletteIndigo
import com.example.ui.theme.PaletteIndigoLight
import com.example.ui.theme.PaletteOrange
import com.example.ui.theme.PaletteYellow
import com.example.util.IntentUtils

@Composable
fun RegionalMapScreen(
    region: DestinationRegion,
    language: AppLanguage,
    onPlaceClick: (PlaceItem) -> Unit,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedCategoryFilter by remember { mutableStateOf<PlaceCategory?>(null) }

    val allPlacesInRegion = remember(region) {
        PlesiranDataSource.places.filter { it.region == region }
    }

    val filteredPlaces = remember(allPlacesInRegion, selectedCategoryFilter) {
        if (selectedCategoryFilter == null) {
            allPlacesInRegion
        } else {
            allPlacesInRegion.filter { it.category == selectedCategoryFilter }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8FB))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            // Screen Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    if (onBackClick != null) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.testTag("map_screen_back_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali ke Beranda",
                                tint = Color(0xFF1E293B)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Column {
                        Text(
                            text = "Denah & Peta Daerah",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E293B)
                            )
                        )
                        Text(
                            text = "Lokasi Wisata, Resto, Oleh-oleh & Hotel di ${region.displayName}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B))
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PaletteIndigoLight
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.MyLocation, contentDescription = null, tint = PaletteIndigo, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${filteredPlaces.size} Lokasi",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PaletteIndigo
                            )
                        )
                    }
                }
            }
        }

        // Hero Map Card with Google Maps Direct Launcher
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                ) {
                    Image(
                        painter = painterResource(id = region.bannerResId),
                        contentDescription = "Peta ${region.displayName}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.35f),
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = PaletteCoral
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Pusat Landmark: ${region.landmarkName}",
                                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = "Denah Wisata ${region.displayName}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Black
                                )
                            )
                            Text(
                                text = "Koordinat: ${region.latitude}, ${region.longitude} • Buka panduan Google Maps",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.9f))
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    IntentUtils.openGoogleMapsSearch(
                                        context = context,
                                        query = "Wisata, Restoran, Oleh-oleh, Hotel di ${region.displayName}"
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PaletteIndigo),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("btn_open_region_google_maps")
                            ) {
                                Icon(imageVector = Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Buka Semua Lokasi di Google Maps", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Category Filter Chips: Wisata, Restoran, Oleh-oleh, Hotel, Hiburan
        item {
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Chip "Semua"
                val isAllSelected = selectedCategoryFilter == null
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isAllSelected) PaletteIndigo else Color.White,
                    border = BorderStroke(1.dp, if (isAllSelected) PaletteIndigo else Color(0xFFCBD5E1)),
                    modifier = Modifier.clickable { selectedCategoryFilter = null }
                ) {
                    Text(
                        text = "Semua (${allPlacesInRegion.size})",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isAllSelected) Color.White else Color(0xFF334155)
                        ),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                    )
                }

                // Wisata
                FilterCategoryChip(
                    title = "Wisata",
                    icon = Icons.Default.Landscape,
                    isSelected = selectedCategoryFilter == PlaceCategory.TOURISM,
                    color = PaletteBerry,
                    onClick = { selectedCategoryFilter = PlaceCategory.TOURISM }
                )

                // Wisata Hiburan
                FilterCategoryChip(
                    title = "Hiburan",
                    icon = Icons.Default.Explore,
                    isSelected = selectedCategoryFilter == PlaceCategory.ENTERTAINMENT,
                    color = PaletteCoral,
                    onClick = { selectedCategoryFilter = PlaceCategory.ENTERTAINMENT }
                )

                // Resto / Kuliner
                FilterCategoryChip(
                    title = "Resto & Kuliner",
                    icon = Icons.Default.Fastfood,
                    isSelected = selectedCategoryFilter == PlaceCategory.CULINARY,
                    color = PaletteOrange,
                    onClick = { selectedCategoryFilter = PlaceCategory.CULINARY }
                )

                // Toko Oleh-oleh
                FilterCategoryChip(
                    title = "Oleh-oleh",
                    icon = Icons.Default.ShoppingBag,
                    isSelected = selectedCategoryFilter == PlaceCategory.SOUVENIR,
                    color = PaletteYellow,
                    onClick = { selectedCategoryFilter = PlaceCategory.SOUVENIR }
                )

                // Hotel & Penginapan
                FilterCategoryChip(
                    title = "Hotel",
                    icon = Icons.Default.Hotel,
                    isSelected = selectedCategoryFilter == PlaceCategory.HOTEL,
                    color = PaletteIndigo,
                    onClick = { selectedCategoryFilter = PlaceCategory.HOTEL }
                )
            }
        }

        // Section Title: Daftar Lokasi Denah
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Daftar Titik Lokasi Denah",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                )
                Text(
                    text = "Terkoneksi GPS Google Maps",
                    style = MaterialTheme.typography.bodySmall.copy(color = PaletteIndigo, fontWeight = FontWeight.SemiBold)
                )
            }
        }

        // List of Locations on the Map with Directions and Details
        items(filteredPlaces, key = { it.id }) { place ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPlaceClick(place) }
                    .testTag("map_place_card_${place.id}")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            // Category Badge
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = place.category.pastelBg
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Icon(imageVector = place.category.icon, contentDescription = null, tint = place.category.accentColor, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = AppStrings.getCategoryTitle(place.category, language),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = place.category.accentColor,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = place.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1E293B)
                                )
                            )

                            Text(
                                text = place.address,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF64748B),
                                    fontSize = 11.5.sp
                                ),
                                maxLines = 2
                            )
                        }

                        // Rating
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFEF3C7)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = PaletteYellow, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "${place.rating}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF78350F)
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                    Spacer(modifier = Modifier.height(8.dp))

                    // Action Buttons for this Map Point: Rute Navigasi & Buka di Google Maps
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                IntentUtils.openGoogleMapsRoute(
                                    context = context,
                                    latitude = place.latitude,
                                    longitude = place.longitude,
                                    destinationName = place.name
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PaletteIndigo),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Rute", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        }

                        OutlinedButton(
                            onClick = {
                                IntentUtils.openGoogleMapsSearch(
                                    context = context,
                                    query = "${place.name} ${place.address}"
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, PaletteIndigo),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null, tint = PaletteIndigo, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Google Maps", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = PaletteIndigo))
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun FilterCategoryChip(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) color else Color.White,
        border = BorderStroke(1.dp, if (isSelected) color else Color(0xFFCBD5E1)),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else color,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.White else Color(0xFF334155)
                )
            )
        }
    }
}
