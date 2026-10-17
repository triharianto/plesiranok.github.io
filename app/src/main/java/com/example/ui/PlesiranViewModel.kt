package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.PlesiranDataSource
import com.example.data.local.FavoritePlaceEntity
import com.example.data.local.PlesiranDatabase
import com.example.data.model.AppLanguage
import com.example.data.model.DestinationRegion
import com.example.data.model.EmergencyContact
import com.example.data.model.LocalPhrase
import com.example.data.model.PlaceCategory
import com.example.data.model.PlaceItem
import com.example.data.repository.PlesiranRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppNavTab {
    EXPLORE,
    TRANSPORT,
    AGENDA,
    MAP,
    TRANSLATOR,
    EMERGENCY,
    SAVED
}

data class PlesiranUiState(
    val selectedRegion: DestinationRegion? = null,
    val selectedLanguage: AppLanguage = AppLanguage.INDONESIA,
    val currentTab: AppNavTab = AppNavTab.EXPLORE,
    val selectedCategory: PlaceCategory? = null,
    val activeCategoryScreen: PlaceCategory? = null,
    val selectedTransportCategory: com.example.data.model.TransportCategory? = null,
    val searchQuery: String = "",
    val selectedPlaceDetail: PlaceItem? = null,
    val selectedTransportDetail: com.example.data.model.TransportItem? = null,
    val selectedAgendaDetail: com.example.data.model.TourismAgenda? = null,
    val isLanguageDialogVisible: Boolean = false,
    val isRegionDialogVisible: Boolean = false,
    val translatorInputText: String = "",
    val translatedOutputText: String = "",
    val phraseFilterCategory: String? = null
)

class PlesiranViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlesiranRepository
    val authManager: com.example.data.auth.AuthManager = com.example.data.auth.AuthManager(application)
    val currentUser = authManager.currentUser
    val isAuthLoading = authManager.isLoading

    private val _uiState = MutableStateFlow(PlesiranUiState())
    val uiState: StateFlow<PlesiranUiState> = _uiState.asStateFlow()

    init {
        val database = PlesiranDatabase.getInstance(application)
        val databaseId = application.getString(com.example.R.string.firestore_database_id)
        val firestore = com.google.firebase.firestore.FirebaseFirestore.getInstance(databaseId)
        repository = PlesiranRepository(database.favoritePlaceDao(), firestore)
    }

    fun signInWithGoogle(activity: android.app.Activity) {
        viewModelScope.launch {
            authManager.signInWithGoogle(activity)
        }
    }

    fun signOut() {
        authManager.signOut()
    }

    val favorites: StateFlow<List<FavoritePlaceEntity>> = repository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredPlaces: StateFlow<List<PlaceItem>> = _uiState
        .combine(favorites) { state, _ ->
            repository.getPlaces(
                region = state.selectedRegion,
                category = state.selectedCategory,
                searchQuery = state.searchQuery
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectRegion(region: DestinationRegion?) {
        _uiState.value = _uiState.value.copy(
            selectedRegion = region,
            isRegionDialogVisible = false
        )
    }

    fun selectLanguage(language: AppLanguage) {
        _uiState.value = _uiState.value.copy(
            selectedLanguage = language,
            isLanguageDialogVisible = false
        )
    }

    fun setNavTab(tab: AppNavTab) {
        _uiState.value = _uiState.value.copy(currentTab = tab)
    }

    fun selectCategory(category: PlaceCategory?) {
        _uiState.value = _uiState.value.copy(
            selectedCategory = if (_uiState.value.selectedCategory == category) null else category
        )
    }

    fun openCategoryScreen(category: PlaceCategory?) {
        _uiState.value = _uiState.value.copy(
            activeCategoryScreen = category,
            selectedCategory = category
        )
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun openPlaceDetail(place: PlaceItem?) {
        _uiState.value = _uiState.value.copy(selectedPlaceDetail = place)
    }

    fun selectTransportCategory(category: com.example.data.model.TransportCategory?) {
        _uiState.value = _uiState.value.copy(selectedTransportCategory = category)
    }

    fun openTransportDetail(item: com.example.data.model.TransportItem?) {
        _uiState.value = _uiState.value.copy(selectedTransportDetail = item)
    }

    fun openAgendaDetail(agenda: com.example.data.model.TourismAgenda?) {
        _uiState.value = _uiState.value.copy(selectedAgendaDetail = agenda)
    }

    fun getTransportItems(): List<com.example.data.model.TransportItem> {
        return repository.getTransportItems(_uiState.value.selectedRegion, _uiState.value.selectedTransportCategory)
    }

    fun getTourismAgendas(): List<com.example.data.model.TourismAgenda> {
        return repository.getTourismAgendas(_uiState.value.selectedRegion)
    }

    fun toggleLanguageDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(isLanguageDialogVisible = show)
    }

    fun toggleRegionDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(isRegionDialogVisible = show)
    }

    fun updateTranslatorInput(text: String) {
        _uiState.value = _uiState.value.copy(
            translatorInputText = text,
            translatedOutputText = generateLocalTranslation(text, _uiState.value.selectedRegion)
        )
    }

    fun filterPhraseCategory(cat: String?) {
        _uiState.value = _uiState.value.copy(phraseFilterCategory = cat)
    }

    fun toggleFavorite(place: PlaceItem) {
        viewModelScope.launch {
            val isFav = favorites.value.any { it.placeId == place.id }
            repository.toggleFavorite(place, isFav)
        }
    }

    fun getEmergencyContacts(): List<EmergencyContact> {
        return repository.getEmergencyContacts(_uiState.value.selectedRegion)
    }

    fun getLocalPhrases(): List<LocalPhrase> {
        return repository.getLocalPhrases(
            _uiState.value.selectedRegion,
            _uiState.value.phraseFilterCategory
        )
    }

    fun isPlaceFavorite(placeId: String): Boolean {
        return favorites.value.any { it.placeId == placeId }
    }

    private fun generateLocalTranslation(input: String, region: DestinationRegion?): String {
        if (input.isBlank()) return ""
        val targetRegion = region ?: DestinationRegion.JOGJA
        val lower = input.trim().lowercase()

        // Quick smart dictionary mapping based on region
        val matchedPhrase = PlesiranDataSource.localPhrases.find {
            it.region == targetRegion && (
                it.indonesian.lowercase().contains(lower) ||
                lower.contains(it.indonesian.lowercase()) ||
                it.englishMeaning.lowercase().contains(lower)
            )
        }
        if (matchedPhrase != null) {
            return "${matchedPhrase.regionalText}\n(Pengucapan: ${matchedPhrase.pronunciation})"
        }

        return when (targetRegion) {
            DestinationRegion.JOGJA -> "Basa Jawa: Terjemahan langsung '$input' dapat dicek lengkap via Google Translate Basa Jawa."
            DestinationRegion.MALANG -> "Boso Walikan / Malangan: Cocokkan kata '$input' dengan kamus osob kiwalan Arema."
            DestinationRegion.BANDUNG -> "Basa Sunda: Tiasa ditaroskeun langsung nganggo Google Translate Basa Sunda."
            DestinationRegion.DENPASAR -> "Basa Bali: Cek kosakata '$input' ring Basa Bali Alus/Madya."
            DestinationRegion.MEDAN -> "Bahasa Medan: Kosakata khas cakap Medan untuk '$input'."
        }
    }
}
