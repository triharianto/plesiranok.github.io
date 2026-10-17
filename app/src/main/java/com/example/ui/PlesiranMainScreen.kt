package com.example.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.datasource.PlesiranDataSource
import com.example.data.localization.AppStrings
import com.example.data.model.DestinationRegion
import com.example.data.model.PlaceCategory
import com.example.data.model.TransportCategory
import com.example.ui.components.CategoryChipsRow
import com.example.ui.components.CategoryPlacesScreen
import com.example.ui.components.DestinationHeader
import com.example.ui.components.EmergencyScreen
import com.example.ui.components.HomeNavigationGrid
import com.example.ui.components.LanguageSelectorDialog
import com.example.ui.components.PlaceCard
import com.example.ui.components.PlaceDetailSheet
import com.example.ui.components.RegionSelectorDialog
import com.example.ui.components.RegionalMapScreen
import com.example.ui.components.SavedScreen
import com.example.ui.components.TourismAgendaScreen
import com.example.ui.components.TranslatorScreen
import com.example.ui.components.TransportScreen
import com.example.ui.theme.PaletteBerry
import com.example.ui.theme.PaletteBerryLight
import com.example.ui.theme.PaletteCoral
import com.example.ui.theme.PaletteCoralLight
import com.example.ui.theme.PaletteIndigo
import com.example.ui.theme.PaletteIndigoLight
import com.example.ui.theme.PaletteOrange
import com.example.ui.theme.PaletteYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlesiranMainScreen(
    viewModel: PlesiranViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val places by viewModel.filteredPlaces.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity
    val currentUser by viewModel.currentUser.collectAsState()

    // Back handling: if category sub-screen is active, close it first. If on other tab, go to Explore.
    BackHandler(enabled = uiState.activeCategoryScreen != null || uiState.currentTab != AppNavTab.EXPLORE) {
        if (uiState.activeCategoryScreen != null) {
            viewModel.openCategoryScreen(null)
        } else {
            viewModel.setNavTab(AppNavTab.EXPLORE)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                // Beranda (Explore) Tab
                NavigationBarItem(
                    selected = uiState.currentTab == AppNavTab.EXPLORE && uiState.activeCategoryScreen == null,
                    onClick = {
                        viewModel.openCategoryScreen(null)
                        viewModel.setNavTab(AppNavTab.EXPLORE)
                    },
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Beranda") },
                    label = {
                        Text(
                            text = AppStrings.getNavExplore(uiState.selectedLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PaletteIndigo,
                        selectedTextColor = PaletteIndigo,
                        indicatorColor = PaletteIndigoLight
                    ),
                    modifier = Modifier.testTag("nav_item_explore")
                )

                // Transportasi (Darat, Laut, Udara)
                NavigationBarItem(
                    selected = uiState.currentTab == AppNavTab.TRANSPORT,
                    onClick = {
                        viewModel.openCategoryScreen(null)
                        viewModel.setNavTab(AppNavTab.TRANSPORT)
                    },
                    icon = { Icon(imageVector = Icons.Default.DirectionsBus, contentDescription = "Transport") },
                    label = {
                        Text(
                            text = AppStrings.getNavTransport(uiState.selectedLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PaletteOrange,
                        selectedTextColor = PaletteOrange,
                        indicatorColor = PaletteIndigoLight
                    ),
                    modifier = Modifier.testTag("nav_item_transport")
                )

                // Agenda Wisata (Kalender)
                NavigationBarItem(
                    selected = uiState.currentTab == AppNavTab.AGENDA,
                    onClick = {
                        viewModel.openCategoryScreen(null)
                        viewModel.setNavTab(AppNavTab.AGENDA)
                    },
                    icon = { Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Agenda") },
                    label = {
                        Text(
                            text = AppStrings.getNavAgenda(uiState.selectedLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PaletteYellow,
                        selectedTextColor = PaletteYellow,
                        indicatorColor = PaletteIndigoLight
                    ),
                    modifier = Modifier.testTag("nav_item_agenda")
                )

                // Map Tab
                NavigationBarItem(
                    selected = uiState.currentTab == AppNavTab.MAP,
                    onClick = {
                        viewModel.openCategoryScreen(null)
                        viewModel.setNavTab(AppNavTab.MAP)
                    },
                    icon = { Icon(imageVector = Icons.Default.Map, contentDescription = "Map") },
                    label = {
                        Text(
                            text = AppStrings.getNavMap(uiState.selectedLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PaletteIndigo,
                        selectedTextColor = PaletteIndigo,
                        indicatorColor = PaletteIndigoLight
                    ),
                    modifier = Modifier.testTag("nav_item_map")
                )

                // Translator Tab
                NavigationBarItem(
                    selected = uiState.currentTab == AppNavTab.TRANSLATOR,
                    onClick = {
                        viewModel.openCategoryScreen(null)
                        viewModel.setNavTab(AppNavTab.TRANSLATOR)
                    },
                    icon = { Icon(imageVector = Icons.Default.Translate, contentDescription = "Translator") },
                    label = {
                        Text(
                            text = AppStrings.getNavTranslator(uiState.selectedLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PaletteBerry,
                        selectedTextColor = PaletteBerry,
                        indicatorColor = PaletteBerryLight
                    ),
                    modifier = Modifier.testTag("nav_item_translator")
                )

                // Emergency Tab
                NavigationBarItem(
                    selected = uiState.currentTab == AppNavTab.EMERGENCY,
                    onClick = {
                        viewModel.openCategoryScreen(null)
                        viewModel.setNavTab(AppNavTab.EMERGENCY)
                    },
                    icon = { Icon(imageVector = Icons.Default.Emergency, contentDescription = "Emergency") },
                    label = {
                        Text(
                            text = AppStrings.getNavEmergency(uiState.selectedLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PaletteCoral,
                        selectedTextColor = PaletteCoral,
                        indicatorColor = PaletteCoralLight
                    ),
                    modifier = Modifier.testTag("nav_item_emergency")
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState.currentTab) {
                AppNavTab.EXPLORE -> {
                    val activeCat = uiState.activeCategoryScreen
                    if (activeCat != null) {
                        // Directly show category places information screen!
                        CategoryPlacesScreen(
                            category = activeCat,
                            region = uiState.selectedRegion ?: DestinationRegion.JOGJA,
                            language = uiState.selectedLanguage,
                            places = PlesiranDataSource.places,
                            onBackClick = { viewModel.openCategoryScreen(null) },
                            onPlaceClick = { viewModel.openPlaceDetail(it) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) },
                            isFavorite = { viewModel.isPlaceFavorite(it) }
                        )
                    } else {
                        ExploreContent(
                            uiState = uiState,
                            places = places,
                            onSearchChange = { viewModel.updateSearchQuery(it) },
                            onRegionClick = { viewModel.toggleRegionDialog(true) },
                            onLanguageClick = { viewModel.toggleLanguageDialog(true) },
                            onCategorySelect = { viewModel.selectCategory(it) },
                            onOpenCategory = { category ->
                                viewModel.openCategoryScreen(category)
                            },
                            onTransportClick = {
                                viewModel.openCategoryScreen(null)
                                viewModel.selectTransportCategory(null)
                                viewModel.setNavTab(AppNavTab.TRANSPORT)
                            },
                            onAgendaClick = {
                                viewModel.openCategoryScreen(null)
                                viewModel.setNavTab(AppNavTab.AGENDA)
                            },
                            onMapClick = {
                                viewModel.openCategoryScreen(null)
                                viewModel.setNavTab(AppNavTab.MAP)
                            },
                            onTranslatorClick = {
                                viewModel.openCategoryScreen(null)
                                viewModel.setNavTab(AppNavTab.TRANSLATOR)
                            },
                            onEmergencyClick = {
                                viewModel.openCategoryScreen(null)
                                viewModel.setNavTab(AppNavTab.EMERGENCY)
                            },
                            onSavedClick = {
                                viewModel.openCategoryScreen(null)
                                viewModel.setNavTab(AppNavTab.SAVED)
                            },
                            onPlaceClick = { viewModel.openPlaceDetail(it) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) },
                            isFavorite = { viewModel.isPlaceFavorite(it) }
                        )
                    }
                }

                AppNavTab.TRANSPORT -> {
                    TransportScreen(
                        region = uiState.selectedRegion ?: DestinationRegion.JOGJA,
                        language = uiState.selectedLanguage,
                        items = viewModel.getTransportItems(),
                        selectedCategory = uiState.selectedTransportCategory,
                        onCategorySelect = { viewModel.selectTransportCategory(it) },
                        onBackClick = { viewModel.setNavTab(AppNavTab.EXPLORE) }
                    )
                }

                AppNavTab.AGENDA -> {
                    TourismAgendaScreen(
                        region = uiState.selectedRegion ?: DestinationRegion.JOGJA,
                        language = uiState.selectedLanguage,
                        agendas = viewModel.getTourismAgendas(),
                        onBackClick = { viewModel.setNavTab(AppNavTab.EXPLORE) }
                    )
                }

                AppNavTab.MAP -> {
                    RegionalMapScreen(
                        region = uiState.selectedRegion ?: DestinationRegion.JOGJA,
                        language = uiState.selectedLanguage,
                        onPlaceClick = { viewModel.openPlaceDetail(it) },
                        onBackClick = { viewModel.setNavTab(AppNavTab.EXPLORE) }
                    )
                }

                AppNavTab.TRANSLATOR -> {
                    TranslatorScreen(
                        region = uiState.selectedRegion ?: DestinationRegion.JOGJA,
                        language = uiState.selectedLanguage,
                        inputText = uiState.translatorInputText,
                        outputText = uiState.translatedOutputText,
                        phrases = viewModel.getLocalPhrases(),
                        selectedCategory = uiState.phraseFilterCategory,
                        onInputTextChange = { viewModel.updateTranslatorInput(it) },
                        onCategoryFilterChange = { viewModel.filterPhraseCategory(it) },
                        onBackClick = { viewModel.setNavTab(AppNavTab.EXPLORE) }
                    )
                }

                AppNavTab.EMERGENCY -> {
                    EmergencyScreen(
                        region = uiState.selectedRegion ?: DestinationRegion.JOGJA,
                        language = uiState.selectedLanguage,
                        contacts = viewModel.getEmergencyContacts(),
                        onBackClick = { viewModel.setNavTab(AppNavTab.EXPLORE) }
                    )
                }

                AppNavTab.SAVED -> {
                    SavedScreen(
                        favorites = favorites,
                        language = uiState.selectedLanguage,
                        onPlaceClick = { viewModel.openPlaceDetail(it) },
                        onRemoveFavorite = { viewModel.toggleFavorite(it) },
                        onExploreClick = { viewModel.setNavTab(AppNavTab.EXPLORE) },
                        onBackClick = { viewModel.setNavTab(AppNavTab.EXPLORE) }
                    )
                }
            }
        }
    }

    // Modal Place Detail BottomSheet
    uiState.selectedPlaceDetail?.let { place ->
        PlaceDetailSheet(
            place = place,
            isFavorite = viewModel.isPlaceFavorite(place.id),
            language = uiState.selectedLanguage,
            onDismiss = { viewModel.openPlaceDetail(null) },
            onToggleFavorite = { viewModel.toggleFavorite(place) }
        )
    }

    // Region Selector Dialog
    if (uiState.isRegionDialogVisible) {
        RegionSelectorDialog(
            currentRegion = uiState.selectedRegion,
            onSelectRegion = { viewModel.selectRegion(it) },
            onDismiss = { viewModel.toggleRegionDialog(false) }
        )
    }

    // Language Selector Dialog
    if (uiState.isLanguageDialogVisible) {
        LanguageSelectorDialog(
            currentLanguage = uiState.selectedLanguage,
            onSelectLanguage = { viewModel.selectLanguage(it) },
            onDismiss = { viewModel.toggleLanguageDialog(false) }
        )
    }
}

@Composable
private fun ExploreContent(
    uiState: PlesiranUiState,
    places: List<com.example.data.model.PlaceItem>,
    onSearchChange: (String) -> Unit,
    onRegionClick: () -> Unit,
    onLanguageClick: () -> Unit,
    onCategorySelect: (PlaceCategory?) -> Unit,
    onOpenCategory: (PlaceCategory) -> Unit,
    onTransportClick: () -> Unit,
    onAgendaClick: () -> Unit,
    onMapClick: () -> Unit,
    onTranslatorClick: () -> Unit,
    onEmergencyClick: () -> Unit,
    onSavedClick: () -> Unit,
    onPlaceClick: (com.example.data.model.PlaceItem) -> Unit,
    onToggleFavorite: (com.example.data.model.PlaceItem) -> Unit,
    isFavorite: (String) -> Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8FB)),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header with "Selamat Berwisata di Indonesia", Region Selector, Language Selector & Search
        item {
            DestinationHeader(
                region = uiState.selectedRegion,
                language = uiState.selectedLanguage,
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = onSearchChange,
                onRegionClick = onRegionClick,
                onLanguageClick = onLanguageClick
            )
        }

        // Prominent Navigation Grid on Home / Beranda: All 12 Navigation Buttons Directly Clickable to Information!
        item {
            HomeNavigationGrid(
                language = uiState.selectedLanguage,
                onCategoryClick = onOpenCategory,
                onTransportClick = onTransportClick,
                onAgendaClick = onAgendaClick,
                onMapClick = onMapClick,
                onTranslatorClick = onTranslatorClick,
                onEmergencyClick = onEmergencyClick,
                onSavedClick = onSavedClick
            )
        }

        // Traveloka-style Category Chips Row
        item {
            CategoryChipsRow(
                selectedCategory = uiState.selectedCategory,
                language = uiState.selectedLanguage,
                onCategorySelected = onCategorySelect
            )
        }

        // Section Title & Place count
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val title = if (uiState.selectedCategory != null) {
                    AppStrings.getCategoryTitle(uiState.selectedCategory, uiState.selectedLanguage)
                } else if (uiState.selectedRegion != null) {
                    "Rekomendasi di ${uiState.selectedRegion.displayName}"
                } else {
                    "Rekomendasi Wisata di Indonesia"
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1E293B)
                    )
                )

                Text(
                    text = "${places.size} Ditemukan",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        // Empty state when search doesn't match
        if (places.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Tidak Ada Hasil",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Coba kata kunci lain atau pilih kategori yang berbeda.",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            // Places Card List
            items(places, key = { it.id }) { place ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    PlaceCard(
                        place = place,
                        isFavorite = isFavorite(place.id),
                        language = uiState.selectedLanguage,
                        onPlaceClick = { onPlaceClick(place) },
                        onToggleFavorite = { onToggleFavorite(place) }
                    )
                }
            }
        }
    }
}
