package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryPlacesScreen(
    category: PlaceCategory,
    region: DestinationRegion,
    language: AppLanguage,
    places: List<PlaceItem>,
    onBackClick: () -> Unit,
    onPlaceClick: (PlaceItem) -> Unit,
    onToggleFavorite: (PlaceItem) -> Unit,
    isFavorite: (String) -> Boolean,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    var selectedSubCategory by remember { mutableStateOf<String?>(null) }
    val categoryTitle = AppStrings.getCategoryTitle(category, language)

    // Subcategory filter chips configuration
    val subCategories = remember(category) {
        when (category) {
            PlaceCategory.ENTERTAINMENT -> listOf("Semua", "Wahana", "Waterpark", "Ecopark", "Museum")
            PlaceCategory.TRANSPORT -> listOf("Semua", "Travel & Shuttle", "Sewa Mobil", "Sewa Motor")
            else -> emptyList()
        }
    }

    val filteredList = remember(places, query, category, region, selectedSubCategory) {
        places.filter { item ->
            val matchCat = item.category == category && item.region == region
            val matchSub = if (selectedSubCategory == null || selectedSubCategory == "Semua") {
                true
            } else {
                item.subCategory?.contains(selectedSubCategory!!, ignoreCase = true) == true
            }
            val matchQuery = if (query.isBlank()) true else {
                item.name.contains(query, ignoreCase = true) ||
                item.address.contains(query, ignoreCase = true) ||
                item.description.contains(query, ignoreCase = true) ||
                item.facilities.any { it.contains(query, ignoreCase = true) } ||
                item.specialties.any { it.contains(query, ignoreCase = true) } ||
                item.vehicleTypes?.contains(query, ignoreCase = true) == true
            }
            matchCat && matchSub && matchQuery
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8FB))
    ) {
        // Top App Bar with Back Button
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = categoryTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1E293B)
                        ),
                        maxLines = 1
                    )
                    Text(
                        text = "${region.displayName} (${region.province})",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PaletteIndigo,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("category_screen_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali ke Beranda",
                        tint = Color(0xFF1E293B)
                    )
                }
            },
            actions = {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = category.pastelBg,
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = category.icon,
                            contentDescription = null,
                            tint = category.accentColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${filteredList.size} Tempat",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = category.accentColor
                            )
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        // Subcategory Filter Chips Row (Wahana, Waterpark, Ecopark, Museum / Travel, Mobil, Motor)
        if (subCategories.isNotEmpty()) {
            val subScroll = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .horizontalScroll(subScroll)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                subCategories.forEach { sub ->
                    val isSelected = (selectedSubCategory == null && sub == "Semua") || selectedSubCategory == sub
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) category.accentColor else Color(0xFFF1F5F9),
                        border = BorderStroke(1.dp, if (isSelected) category.accentColor else Color(0xFFE2E8F0)),
                        modifier = Modifier
                            .clickable {
                                selectedSubCategory = if (sub == "Semua") null else sub
                            }
                            .testTag("sub_chip_$sub")
                    ) {
                        Text(
                            text = sub,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Color(0xFF334155),
                                fontSize = 12.sp
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Search Bar in Category Screen
        Surface(
            color = Color.White,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("category_places_search_field"),
                placeholder = {
                    Text(
                        text = "Cari di ${categoryTitle.lowercase()}...",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray, fontSize = 13.sp)
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = PaletteIndigo)
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PaletteIndigo,
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color(0xFFF8FAFC),
                    unfocusedContainerColor = Color(0xFFF8FAFC)
                )
            )
        }

        // Places List
        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Tidak ada hasil ditemukan",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.Gray)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Coba ubah kata kunci pencarian Anda",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredList, key = { it.id }) { place ->
                    PlaceCard(
                        place = place,
                        language = language,
                        isFavorite = isFavorite(place.id),
                        onPlaceClick = { onPlaceClick(place) },
                        onToggleFavorite = { onToggleFavorite(place) }
                    )
                }
            }
        }
    }
}
